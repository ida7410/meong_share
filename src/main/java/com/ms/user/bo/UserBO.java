package com.ms.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.common.FileManagerService;
import com.ms.user.domain.User;
import com.ms.user.mapper.UserMapper;

@Service
public class UserBO {
	
	@Autowired
	private UserMapper userMapper;

    @Autowired
    private FileManagerService fileManagerService;
	
    // ------- CREATE ------- 
    
    /***
     * Add user
     * @param id
     * @param password
     * @param nickname
     * @param name
     * @param phoneNumber
     * @param email
     */
	public void addUser(
			String id, String password, 
			String nickname, String name,
			String phoneNumber, String email) {
		
		userMapper.insertUser(id, password, nickname, name, phoneNumber, email);
	}
	
	
	// ------- READ ------- 
	
	/***
	 * Get user by primary id
	 * @param id
	 * @return
	 */
	public User getUserById(int id) {
		return userMapper.selectUserById(id);
	}
	
	/***
	 * Get user by login id
	 * @param loginId
	 * @return
	 */
	public User getUserByLoginId(String loginId) {
		User user = userMapper.selectUesrByLoginId(loginId);
		return user;
	}
	
	/***
	 * Get user by login id and email
	 * @param loginId
	 * @param email
	 * @return
	 */
	public User getUserByLoginIdEmail(String loginId, String email) {
		return userMapper.selectUserByLoginIdEmail(loginId, email);
	}
	
	/***
	 * Get user by name and email
	 * @param name
	 * @param email
	 * @return
	 */
	public User getUserByNameEmail(String name, String email) {
		return userMapper.selectUserByNameEmail(name, email);
	}
	
	
	// ------- UPDATE ------- 
	
	/***
	 * Update user password by login id and password
	 * @param loginId
	 * @param password
	 * @param newPassword
	 * @return
	 */
	public int updateUserPasswordByLoginIdPassword(String loginId, String password, String newPassword) {
		return userMapper.updateUserPassword(loginId, password, newPassword);
	}
	
	/***
	 * Update user
	 * @param id
	 * @param loginId
	 * @param nickname
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param profileImageFile
	 */
	public void updateUser(
			int id, String loginId, 
			String nickname, String name,
			String phoneNumber, String email,
			MultipartFile profileImageFile) {
		
		User user = userMapper.selectUserById(id);
		String profileImagePath = null;
		
		// if given is empty set as og value
		if (loginId.equals("")) {
			loginId = user.getLoginId();
		}
		if (nickname.equals("")) {
			nickname = user.getNickname();
		}
		if (name.equals("")) {
			name = user.getName();
		}
		if (phoneNumber.equals("")) {
			phoneNumber = user.getPhoneNumber();
		}
		if (email.equals("")) {
			email = user.getEmail();
		}
		if (profileImageFile == null) {
			profileImagePath = user.getProfileImagePath();
		}
		else {
			profileImagePath = fileManagerService.saveFile(profileImageFile, loginId, "user");
		}
		
		// update user
		userMapper.updateUser(id, loginId, nickname, name, phoneNumber, email, profileImagePath);
	}
	
}
