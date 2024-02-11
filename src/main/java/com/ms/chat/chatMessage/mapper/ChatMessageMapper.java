package com.ms.chat.chatMessage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.chat.chatMessage.domain.ChatMessage;

@Mapper
public interface ChatMessageMapper {
	
	public void insertChatMessage(
			@Param("chatListId") int chatListId,
			@Param("senderId") int senderId,
			@Param("message") String message);
	
	public ChatMessage selectLatestChatMessageByChatListId(int chatListId);
	
	public List<ChatMessage> selectChatMessageListByChatListId(int chatListId);
	
}