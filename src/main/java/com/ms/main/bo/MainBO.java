package com.ms.main.bo;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.chat.bo.ChatListBO;
import com.ms.chat.bo.ChatMessageBO;
import com.ms.chat.domain.ChatList;
import com.ms.chat.domain.ChatMessage;
import com.ms.common.CookieManager;
import com.ms.like.bo.LikeBO;
import com.ms.like.domain.Like;
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
	
	/***
	 * Get card of product
	 * @param userId
	 * @param productId
	 * @return
	 */
	public Card getCardByProductId(Integer userId, int productId) {
		
		// find product
		Product product = productBO.getProductById(productId);
		// find the owner
		User user = userBO.getUserById(product.getOwnerId());
		
		// get the number of like to product and how many time the owner had been recommended and traded
		int likeCount = likeBO.getLikeCountBySubjectIdType(product.getId());
		int recommendCount = likeBO.getRecommendCountBySubjectIdType(user.getId());
		int tradeCount = productBO.getProductListByOwnerIdOrKeyword(user.getId(), null, 0, null, true).size();
		
		// check if user liked the product
		// if not log in default empty
		String liked = "empty-";
		if (userId != null) { // if login
			// check like
			Like like = likeBO.getLikeBySubjectIdUserIdType(productId, userId, "like");
			if (like != null) { // if like exists
				liked = "";
			}
		}
		
		Card card = new Card();
		card.setProduct(product);
		card.setUser(user);
		card.setLikeCount(likeCount);
		card.setRecommendCount(recommendCount);
		card.setTradeCount(tradeCount);
		card.setLiked(liked);
		
		return card;
	}
	
	/***
	 * Get the list of product recently viewed
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public List<Product> getRecentViewProductIdList(HttpServletRequest request) {
		// get recently viewed product id list cookie
		Cookie cookie = cookieManager.getCookie(request, "productList");
		
		// change cookie to list
		List<String> recentViewProductIdStringList = cookieManager.getListByCookie(cookie);
		
		// get product list
		List<Product> recentViewProductList = new ArrayList<>();
		for (String productIdString : recentViewProductIdStringList) {
			// change string to int
			int productId = Integer.parseInt(productIdString);
			
			// get product
			Product product = productBO.getProductById(productId);
			
			// add product to the list
			recentViewProductList.add(product);
		}
		
		return recentViewProductList;
	}
	
	/***
	 * Set the keyword to the list
	 * @param request
	 * @param response
	 * @param keyword
	 * @return
	 */
	public List<String> setCookieList(
			HttpServletRequest request, 
			HttpServletResponse response, 
			String cookieName,
			String keyword) {
		
		// get og keyword list cookie
		Cookie cookie = cookieManager.getCookie(request, cookieName);
		
		// change cookie to list
		List<String> keywordList = cookieManager.getListByCookie(cookie);
		if (keyword == null) { // if keyword x given => reverse the list and return it
			Collections.reverse(keywordList);
			return keywordList;
		}
		
		// check if keyword already exists in the list
		if (keywordList.contains(keyword)) { // if yes remove
			keywordList.remove(keyword);
		}
		
		// 존재한다면 value는 keyword,keyword,keyword,...의 형태
		String keywordListString = null;
		if (cookie != null) { // if list cookie exists
			
			if (keywordList.size() >= 3) { // max 3
				keywordList.remove(0); // remove the first
			}
			
			// add keywrod to the keyword list
			keywordList.add(keyword);
			
			// change list to string form
			keywordListString = String.join(",", keywordList);
		}
		else { // if list cookie x exist
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
	
	/***
	 * Get card list uploaded by user or searched by keyword
	 * @param userLoginId
	 * @param keyword
	 * @param page
	 * @param cri
	 * @param completed
	 * @return
	 */
	public List<Card> getCardByUserLoginIdOrKeyword(String userLoginId, String keyword, int page, Criteria cri, boolean completed) {
		
		List<Card> cardList = new ArrayList<>();

		// is it to find card list uploaded by one user
		Integer userId = null;
		if (userLoginId != null) { // if yes
			// get the primary key id of that user
			userId = userBO.getUserByLoginId(userLoginId).getId();
		}
		
		// skip
		int skip = (page - 1) * cri.getPerPageNum();
		
		// get product list
		// userId null => by keyword or get every product
		// userId x null => uploaded by one user
		List<Product> productList = productBO.getProductListByOwnerIdOrKeyword(userId, keyword, skip, cri.getPerPageNum(), completed);
		
		// get card list
		for (Product product : productList) {
			
			Card card = new Card();
			
			// find the owner of the product
			User user = userBO.getUserById(product.getOwnerId());
			
			// get date difference
			Date now = new Date();
			Date createdAt = product.getCreatedAt();
			long diffSec = (now.getTime() - createdAt.getTime()) / 1000;
			long diffMin = diffSec / 60;
			long diffHrs = diffMin / 60;
			long diffDay = diffHrs / 24;
			String diff = diffDay + "일";
			
			if (diffMin < 1) { // if diff of min < 1 min
				diff = diffSec + "초"; // return diff sec
			}
			else if (diffHrs < 1) { // if diff of min > 1 min & diff hrs < 1 hr
				diff = diffMin + "분"; // return diff min
			}
			else if (diffDay < 1) { // if diff hrs > 1 hr & diff day < 1
				diff = diffHrs + "시간"; // return diff hr
			} // else reutrn diff of day
			
			card.setProduct(product);
			card.setUser(user);
			card.setDiffTime(diff);
			
			cardList.add(card);
		}
		
		return cardList;
	}
	
	/***
	 * Get chat list card where user is included in the chat
	 * @param chatListId
	 * @param userId
	 * @return
	 */
	public List<ChatListCard> getChatListCardList(int userId) {
		
		List<ChatListCard> clcList = new ArrayList<>();
		
		// get the list of chat list
		List<ChatList> clList = chatListBO.getChatListListByUserId(userId);
		
		// get chat list card list
		for (ChatList cl : clList) {
			
			// get product of the chat list
			Product product = productBO.getProductById(cl.getProductId());
			
			// get the latest chat message
			ChatMessage latestCM = chatMessageBO.getLatestChatMessageByChatListId(cl.getId());
			
			ChatListCard clc = new ChatListCard();
			clc.setCl(cl);
			clc.setProduct(product);
			clc.setLatestCM(latestCM);
			
			clcList.add(clc);
		}
		
		return clcList;
	}

	/***
	 * Get the chat card in the chat list
	 * @param chatListId
	 * @return
	 */
	public ChatCard getChatCard(int chatListId) {
		
		// find the chat list & chat message list
		ChatList cl = chatListBO.getChatListById(chatListId);
		List<ChatMessage> cml = chatMessageBO.getChatMessageListByChatListId(chatListId);
		
		// get the product
		Product product = productBO.getProductById(cl.getProductId());
		
		// find the owner and buyer
		User owner = userBO.getUserById(cl.getOwnerId());
		User buyer = userBO.getUserById(cl.getBuyerId());
		
		// find if recommended
		Like like = likeBO.getLikeBySubjectIdUserIdType(owner.getId(), buyer.getId(), product.getId() + "");
		
		ChatCard cc = new ChatCard();
		cc.setChatListId(chatListId);
		cc.setCml(cml);
		cc.setProduct(product);
		cc.setBuyer(buyer);
		cc.setOwner(owner);
		cc.setRecommended(like != null);
		
		return cc;
	}
	
}
