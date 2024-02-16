package com.ms.main.bo;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chat.chatList.bo.ChatListBO;
import com.ms.chat.chatList.domain.ChatList;
import com.ms.chat.chatMessage.bo.ChatMessageBO;
import com.ms.chat.chatMessage.domain.ChatMessage;
import com.ms.common.CookieManager;
import com.ms.like.bo.LikeBO;
import com.ms.main.domain.Card;
import com.ms.main.domain.ChatCard;
import com.ms.main.domain.ChatListCard;
import com.ms.main.domain.Criteria;
import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class MainBO {
	
	@Autowired
	private CookieManager cookieManager;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private ChatListBO chatListBO;

	@Autowired
	private ChatMessageBO chatMessageBO;
	
	
	public Card getCardByProductId(int productId) {
		Product product = productBO.getProductById(productId);
		User user = userBO.getUserById(product.getOwnerId());
		int likeCount = likeBO.getLikeCountBySubjectIdType(product.getId(), "like");
		int recommendCount = likeBO.getLikeCountBySubjectIdType(user.getId(), "recommend");
		
		Card card = new Card();
		card.setProduct(product);
		card.setUser(user);
		card.setLikeCount(likeCount);
		card.setRecommendCount(recommendCount);
		
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
	
	public List<Product> getRecentViewProductIdList(HttpServletRequest request, String cookieName) {
		Cookie cookie = cookieManager.getCookie(request, cookieName);
		List<String> recentViewProductIdStringList = cookieManager.getListByCookie(cookie);
		List<Product> recentViewProductList = new ArrayList<>();
		for (String productIdString : recentViewProductIdStringList) {
			int productId = Integer.parseInt(productIdString);
			Product product = productBO.getProductById(productId);
			recentViewProductList.add(product);
		}
		return recentViewProductList;
	}
	
	public List<String> setKeywordList(HttpServletRequest request, HttpServletResponse response, String cookieName, String keyword) {
		
		Cookie cookie = cookieManager.getCookie(request, cookieName);
		
		List<String> keywordList = cookieManager.getListByCookie(cookie);
		if (keyword == null) {
			Collections.reverse(keywordList);
			return keywordList;
		}
		
		String keywordListString = null;
		if (keywordList.contains(keyword)) { // keyword가 이미 있었다면 지우기
			keywordList.remove(keyword);
		}
		
		// 존재한다면 value는 keyword,keyword,keyword,...의 형태
		if (cookie != null) { 
			if (keywordList.size() >= 3) {
				keywordList.remove(0);
			}
			keywordList.add(keyword);
			keywordListString = String.join(",", keywordList);
		}
		else {
			keywordListString = keyword; // 없다면 단순 keyword로
		}
		
		// 쿠키 추가 / 업데이트
		Cookie c = new Cookie(cookieName, URLEncoder.encode(keywordListString));
		c.setMaxAge(60);
		c.setPath("/");
		response.addCookie(c);
		
		Collections.reverse(keywordList);
		return keywordList;
	}
	
	
	public List<Card> getCardByUserLoginIdOrKeyword(String userLoginId, String keyword, int page, Criteria cri) {
		Integer userId = null;
		if (userLoginId != null) {
			userId = userBO.getUserByLoginId(userLoginId).getId();
		}
		
		List<Card> cardList = new ArrayList<>();
		
		int skip = (page - 1) * cri.getPerPageNum();
		
		
		List<Product> productList = productBO.getProductListByOwnerIdOrKeyword(userId, keyword, skip, cri.getPerPageNum(), false);
		
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
			
			ChatMessage latestCM = chatMessageBO.getLatestChatMessageByChatListId(cl.getId());
			
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
