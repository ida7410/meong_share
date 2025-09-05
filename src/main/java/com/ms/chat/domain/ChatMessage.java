package com.ms.chat.domain;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChatMessage {
	private int id;
	private int chatListId;
	private int senderId;
	private String message;
	private String imagePath;
	private String type;
	private Date createdAt;
}
