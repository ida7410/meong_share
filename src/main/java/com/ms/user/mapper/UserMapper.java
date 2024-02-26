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
	
	public User selectUserById(int id);
	
	public User selectUesrByLoginId(String loginId);
	
	public User selectUserByLoginIdPassword(
			@Param("loginId") String loginId,
			@Param("password") String password);
	
	public User selectUserByLoginIdEmail(
			@Param("loginId") String loginId,
			@Param("email") String email);
	
	public User selectUserByNameEmail(
			@Param("name") String name,
			@Param("email") String email);
	
	public int updateUserPassword(
			@Param("loginId") String loginId,
			@Param("password") String password,
			@Param("newPassword") String newPassword);
	
	public void updateUser(
			@Param("id") int id,
			@Param("loginId") String loginId,
			@Param("nickname") String nickname,
			@Param("name") String name,
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email,
			@Param("profileImagePath") String profileImagePath);
}
