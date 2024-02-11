package com.ms.chat.chatList.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chat.chatList.domain.ChatList;
import com.ms.chat.chatList.mapper.ChatListMapper;

@Service
public class ChatListBO {
	
	@Autowired
	private ChatListMapper chatListMapper;
	
	public int addChatList(int productId, int ownerId, int buyerId) {
		ChatList cl =  new ChatList();
		cl.setProductId(productId);
		cl.setOwnerId(ownerId);
		cl.setBuyerId(buyerId);
		
		chatListMapper.insertChatList(cl);
		int chatListId = cl.getId();
		return chatListId;
	}
	
	public ChatList getChatListByProductIdOwnderIdBuyerId(int productId, int ownerId, int buyerId) {
		return chatListMapper.selectChatListByProductIdOwnderIdBuyerId(productId, ownerId, buyerId);
	}
	
	public ChatList getChatListById(int id) {
		return chatListMapper.selectChatListById(id);
	}
	
	public ChatList getLatestChatListByUserId(int userId) {
		return chatListMapper.selectLatestChatListBySenderIdOrBuyerId(userId);
	}
	
	public List<ChatList> getChatListListByUserId(int userId) {
		return chatListMapper.selectChatListListBySenderIdOrBuyerId(userId);
	}
	
	public void updateChatList(int productId, int ownerId, int buyerId) {
		chatListMapper.updateChatList(productId, ownerId, buyerId);
	}
	
}
