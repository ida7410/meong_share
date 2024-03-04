package com.ms.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.chat.domain.ChatMessage;

@Mapper
public interface ChatMessageMapper {
	
	// ------- CREATE -------
	
	/***
	 * Insert chat message where type is message
	 * @param chatListId
	 * @param senderId
	 * @param message
	 * @param type
	 */
	public void insertChatMessage(
			@Param("chatListId") int chatListId,
			@Param("senderId") int senderId,
			@Param("message") String message,
			@Param("type") String type);
	
	/***
	 * Insert chat message where type is image
	 * @param chatListId
	 * @param senderId
	 * @param imagePath
	 * @param type
	 */
	public void insertChatMessageImage(
			@Param("chatListId") int chatListId,
			@Param("senderId") int senderId,
			@Param("imagePath") String imagePath,
			@Param("type") String type);
	
	
	// ------- READ -------
	
	/***
	 * Select the last chat message in a specific chat list
	 * @param chatListId
	 * @return
	 */
	public ChatMessage selectLatestChatMessageByChatListId(int chatListId);
	
	/***
	 * Select the list of chat message in a specific chat list
	 * @param chatListId
	 * @return
	 */
	public List<ChatMessage> selectChatMessageListByChatListId(int chatListId);
	
}
