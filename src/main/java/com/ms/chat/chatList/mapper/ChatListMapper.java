package com.ms.chat.chatList.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.chat.chatList.domain.ChatList;

@Mapper
public interface ChatListMapper {
	
	public int insertChatList(ChatList cl);
	
	public ChatList selectChatListByProductIdOwnderIdBuyerId(
			@Param("productId") int productId,
			@Param("ownerId") int ownerId,
			@Param("buyerId") int buyerId);
	
	public ChatList selectChatListById(int id);
	
	public ChatList selectLatestChatListBySenderIdOrBuyerId(int userId);
	
	public List<ChatList> selectChatListListBySenderIdOrBuyerId(int userId);
	
	public void updateChatList(
			@Param("productId") int productId,
			@Param("ownerId") int ownerId,
			@Param("buyerId") int buyerId);
}
