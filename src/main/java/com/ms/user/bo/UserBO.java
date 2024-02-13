package com.ms.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.user.domain.User;
import com.ms.user.mapper.UserMapper;

@Service
public class UserBO {
	
	@Autowired
	private UserMapper userMapper;
	
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
	
	public void updateUser(
			int id, String loginId, String password, 
			String nickname, String name,
			String phoneNumber, String email) {
		
		User user = userMapper.selectUserById(id);
		
		if (loginId.equals("")) {
			loginId = user.getLoginId();
		}
		if (password.equals("")) {
			password = user.getPassword();
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
		
		userMapper.updateUser(id, loginId, password, nickname, name, phoneNumber, email);
	}
	
}
