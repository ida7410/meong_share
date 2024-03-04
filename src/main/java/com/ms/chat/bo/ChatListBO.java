package com.ms.chat.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chat.domain.ChatList;
import com.ms.chat.mapper.ChatListMapper;


@Service
public class ChatListBO {
	
	@Autowired
	private ChatListMapper chatListMapper;
	
	// ------- CREATE ------- 
	
	/***
	 * Create chat list
	 * @param productId
	 * @param ownerId
	 * @param buyerId
	 * @return created chat list id
	 */
	public int addChatList(int productId, int ownerId, int buyerId) {
		ChatList cl =  new ChatList();
		cl.setProductId(productId);
		cl.setOwnerId(ownerId);
		cl.setBuyerId(buyerId);
		
		chatListMapper.insertChatList(cl);
		int chatListId = cl.getId();
		return chatListId;
	}
	
	
	// ------- READ ------- 
	
	/***
	 * Get chat list where product id, user id, and buyer id are matched
	 * @param productId
	 * @param ownerId
	 * @param buyerId
	 * @return
	 */
	public ChatList getChatListByProductIdOwnderIdBuyerId(int productId, int ownerId, int buyerId) {
		return chatListMapper.selectChatListByProductIdOwnderIdBuyerId(productId, ownerId, buyerId);
	}
	
	/***
	 * Get chat list using chat list id
	 * @param id
	 * @return
	 */
	public ChatList getChatListById(int id) {
		return chatListMapper.selectChatListById(id);
	}
	
	/***
	 * Get chat list where user sent or received a message
	 * @param userId
	 * @return
	 */
	public ChatList getLatestChatListByUserId(int userId) {
		return chatListMapper.selectLatestChatListBySenderIdOrBuyerId(userId);
	}
	
	/***
	 * Get a chat list using a user id
	 * @param userId
	 * @return
	 */
	public List<ChatList> getChatListListByUserId(int userId) {
		return chatListMapper.selectChatListListBySenderIdOrBuyerId(userId);
	}
	
	
	// ------- UPDATE ------- 
	
	/***
	 * Update chat list to updatedAt = now
	 * @param productId
	 * @param ownerId
	 * @param buyerId
	 */
	public void updateChatList(int productId, int ownerId, int buyerId) {
		chatListMapper.updateChatList(productId, ownerId, buyerId);
	}
	
}
