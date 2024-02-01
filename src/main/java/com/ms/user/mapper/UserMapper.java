package com.ms.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.user.domain.User;

@Mapper
public interface UserMapper {
	
	public void insertUser(
			@Param("id") String id,
			@Param("password") String password,
			@Param("nickname") String nickname,
			@Param("name") String name,
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email);
	
	public User selectUser(
			@Param("loginId") String loginId,
			@Param("password") String password);
}
