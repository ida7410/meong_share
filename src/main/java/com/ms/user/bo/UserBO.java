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
	
	public void addUser(
			String id, String password, 
			String nickname, String name,
			String phoneNumber, String email) {
		
		userMapper.insertUser(id, password, nickname, name, phoneNumber, email);
	}
	
	public User getUserById(int id) {
		return userMapper.selectUserById(id);
	}
	
	public User getUserByLoginId(String loginId) {
		User user = userMapper.selectUesrByLoginId(loginId);
		return user;
	}
	
	public User getUserByLoginIdPassword(String loginId, String password) {
		return userMapper.selectUserByLoginIdPassword(loginId, password);
	}
	
	public User getUserByLoginIdEmail(String loginId, String email) {
		return userMapper.selectUserByLoginIdEmail(loginId, email);
	}
	
	public User getUserByNameEmail(String name, String email) {
		return userMapper.selectUserByNameEmail(name, email);
	}
	
	public int updateUserPasswordByLoginIdPassword(String loginId, String password, String newPassword) {
		return userMapper.updateUserPassword(loginId, password, newPassword);
	}
	
	public void updateUser(
			int id, String loginId, 
			String nickname, String name,
			String phoneNumber, String email,
			MultipartFile profileImageFile) {
		
		User user = userMapper.selectUserById(id);
		String profileImagePath = null;
		
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
			profileImagePath = fileManagerService.saveFile(loginId, profileImageFile);
		}
		
		userMapper.updateUser(id, loginId, nickname, name, phoneNumber, email, profileImagePath);
	}
	
}
