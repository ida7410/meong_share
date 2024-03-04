package com.ms.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.chat.domain.ChatList;


@Mapper
public interface ChatListMapper {
	
	// ------- CREATE -------
	
	/***
	 * Insert chat list
	 * @param cl
	 * @return
	 */
	public int insertChatList(ChatList cl);
	
	
	// ------- READ -------
	
	/***
	 * Select chat list where product id, owner id, and buyer id match
	 * @param productId
	 * @param ownerId
	 * @param buyerId
	 * @return
	 */
	public ChatList selectChatListByProductIdOwnderIdBuyerId(
			@Param("productId") int productId,
			@Param("ownerId") int ownerId,
			@Param("buyerId") int buyerId);
	
	/***
	 * Select chat list using chat list id
	 * @param id
	 * @return
	 */
	public ChatList selectChatListById(int id);
	
	/***
	 * Select the latest chat list where user id equals to sender or buyer id
	 * @param userId
	 * @return
	 */
	public ChatList selectLatestChatListBySenderIdOrBuyerId(int userId);
	
	/***
	 * Select the list of chat list where user id equals to sender or buyer id
	 * @param userId
	 * @return
	 */
	public List<ChatList> selectChatListListBySenderIdOrBuyerId(int userId);
	
	
	// ------- UPDATE -------
	
	/***
	 * Update chat list updatedAt as NOW()
	 * @param productId
	 * @param ownerId
	 * @param buyerId
	 */
	public void updateChatList(
			@Param("productId") int productId,
			@Param("ownerId") int ownerId,
			@Param("buyerId") int buyerId);
}
