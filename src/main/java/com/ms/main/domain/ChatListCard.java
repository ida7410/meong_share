package com.ms.main.domain;

import com.ms.chat.domain.ChatList;
import com.ms.chat.domain.ChatMessage;
import com.ms.product.domain.Product;

import lombok.Data;

@Data
public class ChatListCard {
	private Product product;
	private ChatList cl;
	private ChatMessage latestCM;
}
