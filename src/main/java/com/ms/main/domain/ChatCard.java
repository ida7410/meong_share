package com.ms.main.domain;

import java.util.List;

import com.ms.chat.chatMessage.domain.ChatMessage;
import com.ms.product.domain.Product;
import com.ms.user.domain.User;

import lombok.Data;

@Data
public class ChatCard {
	
	private int chatListId;
	private List<ChatMessage> cml;
	private Product product;
	private User buyer;
	private User owner;
	
}
