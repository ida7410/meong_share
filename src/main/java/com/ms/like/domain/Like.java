package com.ms.like.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Like {
	private int subjectId;
	private int userId;
	private String type;
	private Date createdAt;
}
