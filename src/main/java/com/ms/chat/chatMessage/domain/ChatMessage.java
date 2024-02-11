package com.ms.chat.chatMessage.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMessage {
	private int id;
	private int chatListId;
	private int senderId;
	private String message;
	private Date createdAt;
}
