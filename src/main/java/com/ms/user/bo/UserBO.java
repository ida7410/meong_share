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
	
	public User getUserByLoginIdPassword(String loginId, String password) {
		return userMapper.selectUserByLoginIdPassword(loginId, password);
	}
	
}
