package com.ms.main;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.chat.chatList.bo.ChatListBO;
import com.ms.chat.chatList.domain.ChatList;
import com.ms.main.bo.MainBO;
import com.ms.main.domain.Card;
import com.ms.main.domain.ChatCard;
import com.ms.main.domain.ChatListCard;
import com.ms.main.domain.Criteria;
import com.ms.main.domain.PageMaker;
import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;
import com.ms.user.bo.UserBO;

import jakarta.servlet.http.Cookie;
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
	
	@GetMapping("/home")
	public String home(Model model) {
		List<Product> recentProductList = productBO.getLatestThreeProductList();
		List<Product> recommendProductList = productBO.getThreeRandomProductList();
		
		model.addAttribute("viewName", "main/main");
		model.addAttribute("recentProductList", recentProductList);
		model.addAttribute("recommendProductList", recommendProductList);
		return "template/layout";
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/search")
	public String search(
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page,
			HttpServletResponse response,
			HttpServletRequest request,
			Model model) {
		
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
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(null, keyword, (int)page, cri);
		
		// 모든 쿠키 들고 오기
		Cookie[] cookies = request.getCookies();
		Cookie keywordCookie = null; // keywordList의 쿠키 
		// keywordList의 쿠키만 가져오기
		for (Cookie c : cookies) {
			if (c.getName().equals("keywordList")) {
				keywordCookie = c; // 존재한다면 value는 keyword,keyword,keyword,...의 형태
				break;
			}
		}

		// keyword,keyword,keyword,...의 형태를 리스트로
		List<String> keywordList = new ArrayList<>();
		
		// 단순 상품 검색 x keyword가 존재하고 리스트에 keyword가 없을 때
		if (keyword != null && !keywordList.contains(keyword)) {
			String keywordListString = null;
			
			if (keywordCookie != null) { // 존재한다면 value는 keyword,keyword,keyword,...의 형태
				keywordListString = URLDecoder.decode(keywordCookie.getValue()); // keyword,keyword,keyword,...
				keywordListString = keywordListString + "," + keyword; // ,keyword 추가
			}
			else {
				keywordListString = keyword; // 없다면 단순 keyword로
			}
			
			// 쿠키 추가 / 업데이트
			Cookie cookie = new Cookie("keywordList", URLEncoder.encode(keywordListString));
			cookie.setMaxAge(60);
			response.addCookie(cookie);
			
			// list로 변환
			if (keywordCookie != null) {
				String keywordCookieString = URLDecoder.decode(keywordCookie.getValue()); // 존재한다면 value는 keyword,keyword,keyword,...의 형태
				String[] keywordArray = keywordCookieString.split(","); // array 형태로 변환
				keywordList = Arrays.asList(keywordArray); // array를 리스트로
			}
		}
		
		Collections.reverse(keywordList);
		
		model.addAttribute("viewName", "product/productSearch");
		model.addAttribute("keyword", keyword);
		if (keyword != null) {			
			model.addAttribute("keywordParam", "keyword=" + keyword + "&");
		}
		model.addAttribute("cardList", cardList);
		model.addAttribute("page", page);
		model.addAttribute("pm", pm);
		model.addAttribute("keywordList", keywordList);
		return "template/layout";
	}
	
	@GetMapping("/add-product")
	public String addProduct(Model model) {
		model.addAttribute("viewName", "product/productCreate");		
		return "template/layout";
	}
	
	@GetMapping("/product/{productId}")
	public String productInfo(
			@PathVariable("productId") int productId,
			Model model) {
		
		Card card = mainBO.getCardByProductId(productId);
		List<Product> recommendProductList = productBO.getThreeRandomProductList();
		
		model.addAttribute("viewName", "product/productInfo");
		model.addAttribute("card", card);
		model.addAttribute("recommendProductList", recommendProductList);
		return "template/layout";
	}
	
	@GetMapping("/user/{userLoginId}")
	public String userInfo(
			@PathVariable("userLoginId") String userLoginId,
			@RequestParam(value = "page", required = false) Integer page,
			Model model) {
		
		if (page == null) {
			page = 1;
		}
		
		int totalCount = productBO.getProductCount(null, false);

		Criteria cri = new Criteria();
		cri.setPage(page);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		
		
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(userLoginId, null, (int)page, cri);
		String userNickame = userBO.getUserByLoginId(userLoginId).getNickname();
		
		model.addAttribute("viewName", "user/userInfo");
		model.addAttribute("cardList", cardList);
		model.addAttribute("keywordParam", "");
		model.addAttribute("page", page);
		model.addAttribute("pm", pm);
		model.addAttribute("userNickame", userNickame);
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
	public String myPage(Model model) {
		model.addAttribute("viewName", "myPage/myPage");
		return "template/layout";
	}
}
