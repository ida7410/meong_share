package com.ms.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@PostMapping("/signUp")
	public Map<String, Object> signUp(
			@RequestParam("id") String id,
			@RequestParam("password") String password,
			@RequestParam("nickname") String nickname,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email) {
		
		Map<String, Object> result = new HashMap<>();
		
		// DB insert
		userBO.addUser(id, password, nickname, name, phoneNumber, email);
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
	@PostMapping("/logIn")
	public Map<String, Object> logIn(
			@RequestParam("id") String loginId,
			@RequestParam("password") String password,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// DB select
		User user = userBO.getUserByLoginIdPassword(loginId, password);
		if (user == null) {
			result.put("code", 300);
			result.put("error_message", "아이디/비밀번호가 일치하지 않습니다.");
			return result;
		}
		
		session.setAttribute("userId", user.getId());
		session.setAttribute("userLoginId", user.getLoginId());
		session.setAttribute("userNickame", user.getNickname());
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
	@PostMapping("/check-duplicated-id")
	public Map<String, Object> checkDuplicatedId(
			@RequestParam("id") String id) {
		
		Map<String, Object> result = new HashMap<>();
		
		User user = userBO.getUserByLoginId(id);
		if (user == null) {
			result.put("isDuplicateId", false);
		}
		else {
			result.put("isDuplicateId", true);
		}
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
	@PostMapping("/update")
	public Map<String, Object> update(
			@RequestParam(value = "loginId", required = false) String loginId,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "nickname", required = false) String nickname,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "email", required = false) String email,
			HttpSession session) {

		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 300);
			result.put("error_message", "세션이 만료되었습니다. 로그인해주세요.");
			return result;
		}
		
		// DB update
		userBO.updateUser(userId, loginId, password, nickname, name, phoneNumber, email);

		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
}
