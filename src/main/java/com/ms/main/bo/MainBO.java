package com.ms.main.bo;

import java.net.URLDecoder;
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
	
	public List<String> setKeywordList(Cookie keywordCookie, HttpServletResponse response, String keyword) {
		
		List<String> keywordList = cookieManager.getListByCookie(keywordCookie);
		if (keyword == null) {
			Collections.reverse(keywordList);
			return keywordList;
		}
		
		// 단순 상품 검색 x keyword가 존재하고 리스트에 keyword가 없을 때
		String keywordListString = null;
		if (!keywordList.contains(keyword)) {
			
			if (keywordCookie != null) { // 존재한다면 value는 keyword,keyword,keyword,...의 형태
				keywordListString = URLDecoder.decode(keywordCookie.getValue()); // keyword,keyword,keyword,...
				keywordListString = keywordListString + "," + keyword; // ,keyword 추가
			}
			else {
				keywordListString = keyword; // 없다면 단순 keyword로
			}
			
		}
		else {
			keywordList.remove(keyword);
		}
		
		// 쿠키 추가 / 업데이트
		Cookie cookie = new Cookie("keywordList", URLEncoder.encode(keywordListString));
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		keywordList.add(keyword);
		
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
