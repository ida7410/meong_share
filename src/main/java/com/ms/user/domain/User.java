package com.ms.user.domain;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	private int id;
	private String loginId;
	private String password;
	private String nickname;
	private String name;
	private String phoneNumber;
	private String email;
	private String profileImagePath;
	private Date createdAt;
	private Date updatedAt;
}
