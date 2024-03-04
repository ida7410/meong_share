package com.ms.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.user.domain.User;

@Mapper
public interface UserMapper {
	
	// ------- CREATE ------- 
	
	/***
	 * Insert user
	 * @param id
	 * @param password
	 * @param nickname
	 * @param name
	 * @param phoneNumber
	 * @param email
	 */
	public void insertUser(
			@Param("id") String id,
			@Param("password") String password,
			@Param("nickname") String nickname,
			@Param("name") String name,
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email);
	
	
	// ------- READ ------- 
	
	/***
	 * Select user by user id
	 * @param id
	 * @return
	 */
	public User selectUserById(int id);
	
	/***
	 * Select user by user login id
	 * @param loginId
	 * @return
	 */
	public User selectUesrByLoginId(String loginId);

	/***
	 * Select user by login id and email
	 * @param loginId
	 * @param email
	 * @return
	 */
	public User selectUserByLoginIdEmail(
			@Param("loginId") String loginId,
			@Param("email") String email);
	
	/***
	 * Select user by name and email
	 * @param name
	 * @param email
	 * @return
	 */
	public User selectUserByNameEmail(
			@Param("name") String name,
			@Param("email") String email);
	
	
	// ------- UPDATE ------- 
	
	/***
	 * Update user password by login id and password
	 * @param loginId
	 * @param password
	 * @param newPassword
	 * @return
	 */
	public int updateUserPassword(
			@Param("loginId") String loginId,
			@Param("password") String password,
			@Param("newPassword") String newPassword);
	
	/***
	 * Update user
	 * @param id
	 * @param loginId
	 * @param nickname
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param profileImagePath
	 */
	public void updateUser(
			@Param("id") int id,
			@Param("loginId") String loginId,
			@Param("nickname") String nickname,
			@Param("name") String name,
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email,
			@Param("profileImagePath") String profileImagePath);
}
