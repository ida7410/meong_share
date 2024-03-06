package com.ms.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.common.Sha256;
import com.ms.mail.bo.MailBO;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private MailBO mailBO;
	
	@Autowired
	private Sha256 sha256;
	
	/***
	 * Sign up = add user
	 * @param id
	 * @param password
	 * @param nickname
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @return
	 */
	@PostMapping("/signUp")
	public Map<String, Object> signUp(
			@RequestParam("id") String id,
			@RequestParam("password") String password,
			@RequestParam("nickname") String nickname,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email) {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			// encrypt 3 times
			String encrypted = sha256.encrypt(sha256.encrypt(sha256.encrypt(password)));

			// DB insert
			userBO.addUser(id, encrypted, nickname, name, phoneNumber, email);
			
			result.put("code", 200);
			result.put("result", "success");
			
		} catch (NoSuchAlgorithmException e) {
			result.put("code", 400);
			result.put("error_message", "비밀번호 저장에 실패했습니다.");
		}
		
		return result;
	}
	
	/***
	 * Login
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/logIn")
	public Map<String, Object> logIn(
			@RequestParam("id") String loginId,
			@RequestParam("password") String password,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// find user matching login id and password
		User user = userBO.getUserByLoginId(loginId);
		try {
			// encrypt given password
			String encrypted = sha256.encrypt(sha256.encrypt(sha256.encrypt(password)));
			
			// check encrypted pw equals db user pw
			if (!encrypted.equals(user.getPassword())) { // if not
				result.put("code", 300);
				result.put("error_message", "아이디/비밀번호가 일치하지 않습니다.");
				return result;
			}
			
			// set session
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickname", user.getNickname());
			
			result.put("code", 200);
			result.put("result", "success");
			
		} catch (NoSuchAlgorithmException e) {
			result.put("code", 400);
			result.put("error_message", "비밀번호 확인에 실패했습니다.");
		}
		
		return result;
	}
	
	/***
	 * Check if id is duplicated
	 * @param id
	 * @return
	 */
	@PostMapping("/check-duplicated-id")
	public Map<String, Object> checkDuplicatedId(
			@RequestParam("id") String id) {
		
		Map<String, Object> result = new HashMap<>();
		
		// get user by login id
		User user = userBO.getUserByLoginId(id);
		if (user == null) { // if user x exist
			result.put("isDuplicateId", false);
		}
		else { // if user exists
			result.put("isDuplicateId", true);
		}
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}

	/***
	 * Check email
	 * @param email
	 * @return
	 */
	@PostMapping("/check-email-code")
	public Map<String, Object> checkEmailCode(
			@RequestParam("email") String email) {
		
		Map<String, Object> result = new HashMap<>();
		
		// create code
		String randomChar = getRandomChar();
		
		// send email including code
		mailBO.mailSend(email, 
					"[MEONG SHAR] 이메일 인증번호", 
					"멍셰어 이메일 인증번호: " + randomChar);
		
		result.put("code", 200);
		result.put("randomChar", randomChar);
		return result;
	}
	
	/***
	 * Find id
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/findId")
	public Map<String, Object> findId(
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		Map<String, Object> result = new HashMap<>();
		
		// find user by name and email
		User user = userBO.getUserByNameEmail(name, email);
		if (user == null) { // if user x exist
			result.put("code", 300);
			result.put("message", "사용자를 찾을 수 없습니다.");
		}
		else { // if exists
			
			// send email including id
			mailBO.mailSend(email, 
						"[MEONG SHAR] 아이디 찾기", 
						"멍셰어 아이디: " + user.getLoginId());
			
			result.put("code", 200);
			result.put("message", "아이디를 보내드렸습니다. 이메일을 확인해주세요.");
		}
		
		result.put("result", "success");
		
		return result;
	}
	
	/***
	 * Find password
	 * @param id
	 * @param email
	 * @return
	 */
	@PostMapping("/findPw")
	public Map<String, Object> findPw(
			@RequestParam("id") String id,
			@RequestParam("email") String email) {
		
		Map<String, Object> result = new HashMap<>();
		
		// find user by login id and email
		User user = userBO.getUserByLoginIdEmail(id, email);
		if (user == null) { // if user x exist
			result.put("code", 300);
			result.put("message", "사용자를 찾을 수 없습니다.");
		}
		else { // if exists
			
			// create code
			String randomChar = getRandomChar();
			
			// send email with code
			mailBO.mailSend(email, 
						"[MEONG SHAR] 비밀번호 찾기 - 인증번호", 
						"멍셰어 비밀번호 찾기 - 인증번호: " + randomChar);
			
			result.put("code", 200);
			result.put("randomChar", randomChar);
		}
		
		result.put("result", "success");
		
		return result;
	}
	
	// Create random string
	public String getRandomChar() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
				'u', 'v', 'w', 'x', 'y', 'z'};

		String str = "";

		// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}
	
	/***
	 * Create a temporary password and update it
	 * @param id
	 * @return
	 */
	@PostMapping("/updateTempPw")
	public Map<String, Object> updateTempPw(
			@RequestParam("id") String id) {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			
			// create temp password
			String tempPassword = getRandomChar();
			String encrypted = sha256.encrypt(sha256.encrypt(sha256.encrypt(tempPassword)));
			
			// get user by login id
			User user = userBO.getUserByLoginId(id);
			
			// update temporary password by login id and password
			userBO.updateUserPasswordByLoginIdPassword(id, user.getPassword(), encrypted);
			
			// send mail with temporary password
			mailBO.mailSend(user.getEmail(), 
					"[MEONG SHARE] 임시 비밀번호", 
					"멍셰어 임시비밀번호: " + tempPassword);
			
			result.put("code", 200);
			result.put("result", "success");
			
		} catch (NoSuchAlgorithmException e) {
			result.put("code", 400);
			result.put("error_message", "임시 비밀번호 저장에 실패했습니다.");
		}
		return result;
	}
	
	/***
	 * Update password
	 * @param password
	 * @param newPassword
	 * @param session
	 * @return
	 */
	@PostMapping("/update-pw")
	public Map<String, Object> updatePw(
			@RequestParam("password") String password,
			@RequestParam("newPassword") String newPassword,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// check login status
		String id = (String)session.getAttribute("userLoginId");
		if (id == null) {
			result.put("code", 300);
			result.put("error_message", "세션이 만료되었습니다. 다시 로그인해주세요.");
			return result;
		}
		
		// find user to check password
		User user = userBO.getUserByLoginId(id);
		try {
			
			// encrypt given password
			String encrypted = sha256.encrypt(sha256.encrypt(sha256.encrypt(password)));

			// check encrypted pw equals db user pw
			if (!encrypted.equals(user.getPassword())) { // if not
				result.put("code", 300);
				result.put("error_message", "아이디/비밀번호가 일치하지 않습니다.");
				return result;
			}

			// update password
			String newEncrypted = sha256.encrypt(sha256.encrypt(sha256.encrypt(newPassword)));
			userBO.updateUserPasswordByLoginIdPassword(id, encrypted, newEncrypted);

			result.put("code", 200);
			result.put("result", "success");

		} catch (NoSuchAlgorithmException e) {
			result.put("code", 400);
			result.put("error_message", "비밀번호 확인에 실패했습니다.");
		}
		
		return result;
	}
	
	/***
	 * Update user info
	 * @param loginId
	 * @param nickname
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param profileImageFile
	 * @param session
	 * @return
	 */
	@PostMapping("/update")
	public Map<String, Object> update(
			@RequestParam(value = "loginId", required = false) String loginId,
			@RequestParam(value = "nickname", required = false) String nickname,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "profileImageFile", required = false) MultipartFile profileImageFile,
			HttpSession session) {

		Map<String, Object> result = new HashMap<>();
		
		// check login status
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 300);
			result.put("error_message", "세션이 만료되었습니다. 로그인해주세요.");
			return result;
		}
		
		// DB update
		userBO.updateUser(userId, loginId, nickname, name, phoneNumber, email, profileImageFile);
		
		session.setAttribute("userNickname", nickname);

		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
}
