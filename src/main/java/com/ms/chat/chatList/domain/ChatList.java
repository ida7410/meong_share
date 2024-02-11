package com.ms.chat.chatList.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ChatList {
	private int id;
	private int productId;
	private int ownerId;
	private int buyerId;
	private Date createdAt;
	private Date updatedAt;
}
