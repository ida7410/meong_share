package com.ms.main.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chat.chatList.bo.ChatListBO;
import com.ms.chat.chatList.domain.ChatList;
import com.ms.chat.chatMessage.bo.ChatMessageBO;
import com.ms.chat.chatMessage.domain.ChatMessage;
import com.ms.main.domain.Card;
import com.ms.main.domain.ChatCard;
import com.ms.main.domain.ChatListCard;
import com.ms.main.domain.Criteria;
import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

@Service
public class MainBO {
	
	private static final int CARD_LIMIT = 8;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ChatListBO chatListBO;

	@Autowired
	private ChatMessageBO chatMessageBO;
	
	
	public Card getCardByProductId(int productId) {
		Product product = productBO.getProductById(productId);
		User user = userBO.getUserById(product.getOwnerId());
		
		Card card = new Card();
		card.setProduct(product);
		card.setUser(user);
		
		return card;
	}
	
	/*
	 * public List<Card> getCardByUserLoginId(String userLoginId, int page, int
	 * prevProductId) { List<Card> cardList = new ArrayList<>();
	 * 
	 * User user = userBO.getUserByLoginId(userLoginId); int userId = user.getId();
	 * List<Product> userProductList = productBO.getProductListByOwnerId(userId);
	 * 
	 * for (Product product : userProductList) { Card card = new Card();
	 * 
	 * card.setProduct(product); card.setUser(user);
	 * 
	 * cardList.add(card); }
	 * 
	 * Collections.reverse(cardList);
	 * 
	 * return cardList; }
	 * 
	 * public List<Card> getCardByKeyword(String keyword, int page, Criteria cri) {
	 * List<Card> cardList = new ArrayList<>();
	 * 
	 * int skip = (page - 1) * cri.getPerPageNum();
	 * 
	 * List<Product> productList = productBO.getProductList(keyword, skip,
	 * cri.getPerPageNum());
	 * 
	 * for (Product product : productList) { Card card = new Card();
	 * 
	 * User user = userBO.getUserById(product.getOwnerId());
	 * 
	 * card.setProduct(product); card.setUser(user);
	 * 
	 * cardList.add(card); }
	 * 
	 * Collections.reverse(cardList);
	 * 
	 * return cardList; }
	 */
	
	
	public List<Card> getCardByUserLoginIdOrKeyword(String userLoginId, String keyword, int page, Criteria cri) {
		Integer userId = null;
		if (userLoginId != null) {
			userId = userBO.getUserByLoginId(userLoginId).getId();
		}
		
		List<Card> cardList = new ArrayList<>();
		
		int skip = (page - 1) * cri.getPerPageNum();
		
		
		List<Product> productList = productBO.getProductListByOwnerIdOrKeyword(userId, keyword, skip, cri.getPerPageNum());
		
		for (Product product : productList) {
			Card card = new Card();
			
			User user = userBO.getUserById(product.getOwnerId());
			
			card.setProduct(product);
			card.setUser(user);
			
			cardList.add(card);
		}
		
		Collections.reverse(cardList);
		
		return cardList;
	}
	
	public List<ChatListCard> getChatListCard(int chatListId, int userId) {
		List<ChatListCard> clcList = new ArrayList<>();
		
		List<ChatList> clList = chatListBO.getChatListListByUserId(userId);
		
		for (ChatList cl : clList) {
			ChatListCard clc = new ChatListCard();
			
			Product product = productBO.getProductById(cl.getProductId());
			
			ChatMessage latestCM = chatMessageBO.getLatestChatMessageByChatListId(chatListId);
			
			clc.setCl(cl);
			clc.setProduct(product);
			clc.setLatestCM(latestCM);
			
			clcList.add(clc);
		}
		
		return clcList;
	}
	
	public ChatCard getChatCard(int chatListId) {
		ChatCard cc = new ChatCard();
		
		ChatList cl = chatListBO.getChatListById(chatListId);
		List<ChatMessage> cml = chatMessageBO.getChatMessageListByChatListId(chatListId);
		
		Product product = productBO.getProductById(cl.getProductId());
		
		User owner = userBO.getUserById(cl.getOwnerId());
		User buyer = userBO.getUserById(cl.getBuyerId());
		
		cc.setChatListId(chatListId);
		cc.setCml(cml);
		cc.setProduct(product);
		cc.setBuyer(buyer);
		cc.setOwner(owner);
		
		return cc;
	}
	
}
