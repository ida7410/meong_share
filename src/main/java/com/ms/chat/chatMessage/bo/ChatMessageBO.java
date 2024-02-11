package com.ms.chat.chatMessage.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chat.chatList.bo.ChatListBO;
import com.ms.chat.chatList.domain.ChatList;
import com.ms.chat.chatMessage.domain.ChatMessage;
import com.ms.chat.chatMessage.mapper.ChatMessageMapper;

@Service
public class ChatMessageBO {
	
	@Autowired
	private ChatMessageMapper chatMessageMapper;
	
	@Autowired
	private ChatListBO chatListBO;
	
	public void addChatMessage(int chatListId, int userId, String message) {
		chatMessageMapper.insertChatMessage(chatListId, userId, message);
		ChatList cl = chatListBO.getChatListById(chatListId);
		
		chatListBO.updateChatList(cl.getProductId(), cl.getOwnerId(), cl.getBuyerId());
	}

	public ChatMessage getLatestChatMessageByChatListId(int chatListId) {
		return chatMessageMapper.selectLatestChatMessageByChatListId(chatListId);
	}
	
	public List<ChatMessage> getChatMessageListByChatListId(int chatListId) {
		return chatMessageMapper.selectChatMessageListByChatListId(chatListId);
	}
	
}
