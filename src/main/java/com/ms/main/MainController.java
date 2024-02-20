package com.ms.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.chat.chatList.bo.ChatListBO;
import com.ms.chat.chatList.domain.ChatList;
import com.ms.common.CookieManager;
import com.ms.like.bo.LikeBO;
import com.ms.main.bo.MainBO;
import com.ms.main.domain.Card;
import com.ms.main.domain.ChatCard;
import com.ms.main.domain.ChatListCard;
import com.ms.main.domain.Criteria;
import com.ms.main.domain.PageMaker;
import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
	@Autowired
	private MainBO mainBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ChatListBO chatListBO;
	
	@Autowired
	private LikeBO likeBO;
	
	@GetMapping("/home")
	public String home(Model model) {
		List<Product> recentProductList = productBO.getLatestThreeProductList();
		List<Product> recommendProductList = productBO.getThreeRandomProductList();
		
		model.addAttribute("viewName", "main/main");
		model.addAttribute("recentProductList", recentProductList);
		model.addAttribute("recommendProductList", recommendProductList);
		return "template/layout";
	}
	
	@GetMapping("/search")
	public String search(
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page,
			HttpServletResponse response,
			HttpServletRequest request,
			Model model) {
		
		log.info(keyword);
		if (page == null) {
			page = 1;
		}
		if (keyword == null) {
			keyword = null;
		}
		
		int totalCount = productBO.getProductCount(keyword, false);
		
		Criteria cri = new Criteria();
		cri.setPage(page);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		
		// DB select
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(null, keyword, (int)page, cri, false);
		
		// cookie set
		List<String> keywordList = mainBO.setKeywordList(request, response, "keywordList", keyword);
		
		// get recent view produt
		List<Product> recentViewProductList = mainBO.getRecentViewProductIdList(request, "productList");
		
		model.addAttribute("viewName", "product/productSearch");
		model.addAttribute("keyword", keyword);
		if (keyword != null) {			
			model.addAttribute("keywordParam", "keyword=" + keyword + "&");
		}
		model.addAttribute("cardList", cardList);
		model.addAttribute("page", page);
		model.addAttribute("pm", pm);
		model.addAttribute("keywordList", keywordList);
		model.addAttribute("recentViewProductList", recentViewProductList);
		return "template/layout";
	}
	
	@GetMapping("/add-product")
	public String addProduct(Model model) {
		model.addAttribute("viewName", "product/productCreate");		
		return "template/layout";
	}
	
	@GetMapping("/map")
	public String map(Model model) {
		model.addAttribute("viewName", "map/map");		
		return "template/layout";
	}
	
	@GetMapping("/product/{productId}")
	public String productInfo(
			@PathVariable("productId") int productId,
			HttpServletResponse response,
			HttpServletRequest request,
			HttpSession session,
			Model model) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		Card card = mainBO.getCardByProductId(userId, productId);
		List<Product> recommendProductList = productBO.getThreeRandomProductList();
		
		mainBO.setKeywordList(request, response, "productList", productId + "");
		
		model.addAttribute("viewName", "product/productInfo");
		model.addAttribute("card", card);
		model.addAttribute("recommendProductList", recommendProductList);
		return "template/layout";
	}
	
	@GetMapping("/user/{userLoginId}")
	public String userInfo(
			@PathVariable("userLoginId") String userLoginId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "completed", required = false) boolean completed,
			Model model) {
		
		if (page == null) {
			page = 1;
		}
		
		User user = userBO.getUserByLoginId(userLoginId);

		int tradeCount = productBO.getProductCount(null, true);
		int recommendCount = likeBO.getRecommendCountBySubjectIdType(user.getId());

		int totalCount = productBO.getProductCount(null, false);

		Criteria cri = new Criteria();
		cri.setPage(page);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(userLoginId, null, (int)page, cri, completed);
		
		model.addAttribute("viewName", "user/userInfo");
		model.addAttribute("cardList", cardList);
		model.addAttribute("page", page);
		model.addAttribute("pm", pm);
		model.addAttribute("user", user);
		model.addAttribute("recommendCount", recommendCount);
		model.addAttribute("tradeCount", tradeCount);
		return "template/layout";
	}
	
	@GetMapping("/chat")
	public String chatList(HttpSession session) {
		
		// DB select (latest chat list)
		ChatList latestChatList = chatListBO.getLatestChatListByUserId((Integer)session.getAttribute("userId"));
		if (latestChatList == null) {
			return "redirect:/home";
		}
		
		return "redirect:chat/" + latestChatList.getId();
	}
	
	@GetMapping("/chat/{chatListId}")
	public String chatList(
			@PathVariable("chatListId") Integer chatListId,
			HttpSession session,
			Model model) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		List<ChatListCard> clcList = mainBO.getChatListCard(chatListId, userId);
		
		ChatCard cc = mainBO.getChatCard(chatListId);
		
		model.addAttribute("viewName", "chat/chatList");
		model.addAttribute("chatListCardList", clcList);
		model.addAttribute("chatListId", chatListId);
		model.addAttribute("chatCard", cc);
		return "template/layout";
	}

	@GetMapping("/my-page")
	public String myPage(HttpSession session, Model model) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		User user = userBO.getUserById(userId);
		String userProfileImagePath = user.getProfileImagePath();
		
		model.addAttribute("userProfileImagePath", userProfileImagePath);
		model.addAttribute("viewName", "myPage/myPage");
		return "template/layout";
	}
}
