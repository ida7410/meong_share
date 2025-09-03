package com.ms.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.chat.bo.ChatListBO;
import com.ms.chat.domain.ChatList;
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

@Controller
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

	/***
	 * update vet list view
	 * @param model
	 * @return
	 */
	@GetMapping("/update-vetlist")
	public String updateVetListView(Model model) {
//		 vetBO.updateVet();
		model.addAttribute("viewName", "map/new");
		return "template/layout";
	}
	
	/***
	 * If path is empty redirect to home
	 * @return
	 */
	@GetMapping("")
	public String empty() {
		return "redirect:/home";
	}
	
	/***
	 * Main view (home)
	 * @param model
	 * @return
	 */
	@GetMapping("/home")
	public String home(Model model) {
		
		// get recent uploaded product list & random three product list
		List<Product> recentProductList = productBO.getLatestThreeProductList();
		List<Product> recommendProductList = productBO.getThreeRandomProductList();
		
		model.addAttribute("viewName", "main/main");
		model.addAttribute("title", "홈 / ");
		model.addAttribute("recentProductList", recentProductList);
		model.addAttribute("recommendProductList", recommendProductList);
		return "template/layout";
	}
	
	/***
	 * Search view & get searched result
	 * @param keyword
	 * @param page
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public String search(
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page,
			HttpServletResponse response,
			HttpServletRequest request,
			Model model) {
		
		// if no page is represented set page = 1
		if (page == null) {
			page = 1;
		}
		// if keyword does not exist set null
		if (keyword == null) {
			keyword = null;
		}
		
		// total number of products
		int totalCount = productBO.getProductCountByKeywordCompleted(keyword, false);
		
		Criteria cri = new Criteria();
		cri.setPage(page);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		
		// DB select
		// create card with incompleted product searching keyword
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(null, keyword, (int)page, cri, false);
		
		// cookie set
		List<String> keywordList = mainBO.setCookieList(request, response, "keywordList", keyword);
		
		// get recent view product
		List<Product> recentViewProductList = mainBO.getRecentViewProductIdList(request);
		
		model.addAttribute("viewName", "product/productSearch");
		model.addAttribute("title", "검색 / ");
		model.addAttribute("keyword", keyword);
		if (keyword != null) { // if keyword exists make keyword parameter		
			model.addAttribute("keywordParam", "keyword=" + keyword + "&");
		}
		model.addAttribute("cardList", cardList);
		model.addAttribute("page", page);
		model.addAttribute("pm", pm);
		model.addAttribute("keywordList", keywordList);
		model.addAttribute("recentViewProductList", recentViewProductList);
		return "template/layout";
	}
	
	/***
	 * Adding product view
	 * @param model
	 * @return
	 */
	@GetMapping("/add-product")
	public String addProduct(Model model) {
		model.addAttribute("viewName", "product/productCreate");
		model.addAttribute("title", "물품 등록 / ");
		return "template/layout";
	}
	
	/***
	 * Map for vet list view
	 * @param model
	 * @return
	 */
	@GetMapping("/map")
	public String map(Model model) {
		model.addAttribute("viewName", "map/map");		
		model.addAttribute("title", "지도 / ");		
		return "template/layout";
	}
	
	/***
	 * More info view about product
	 * @param productId
	 * @param response
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/product/{productId}")
	public String productInfo(
			@PathVariable("productId") int productId,
			HttpServletResponse response,
			HttpServletRequest request,
			HttpSession session,
			Model model) {
		
		// get user id in session
		Integer userId = (Integer)session.getAttribute("userId");
		
		// make card for product
		Card card = mainBO.getCardByProductId(userId, productId);
		
		// get random 3 recommended products
		List<Product> recommendProductList = productBO.getThreeRandomProductList();
		
		// set searched product list in cookie
		mainBO.setCookieList(request, response, "productList", productId + "");
		
		model.addAttribute("viewName", "product/productInfo");
		model.addAttribute("title", "물품 정보: " + card.getProduct().getName() + " / ");
		model.addAttribute("card", card);
		model.addAttribute("recommendProductList", recommendProductList);
		return "template/layout";
	}
	
	/***
	 * Info view of a user
	 * @param userLoginId
	 * @param page
	 * @param completed
	 * @param model
	 * @return
	 */
	@GetMapping("/user/{userLoginId}")
	public String userInfo(
			@PathVariable("userLoginId") String userLoginId,
			@RequestParam(value = "page", required = false) Integer page,
			Model model) {
		
		// if page is null set 1
		if (page == null) {
			page = 1;
		}
		
		// find user by user login id
		User user = userBO.getUserByLoginId(userLoginId);
		
		// get the total number founded user has traded & recommended
		int tradeCount = productBO.getProductListByOwnerIdOrKeyword(user.getId(), null, 0, null, true).size();
		int recommendCount = likeBO.getRecommendCountBySubjectIdType(user.getId());
		
		// get total number of incompleted products
		int totalCount = productBO.getProductListByOwnerIdOrKeyword(user.getId(), null, 0, null, false).size();

		Criteria cri = new Criteria();
		cri.setPage(page);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		
		// get card list
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(userLoginId, null, (int)page, cri, false);
		
		model.addAttribute("viewName", "user/userInfo");
		model.addAttribute("title", "사용자 정보 : " + user.getNickname() + " / ");
		model.addAttribute("cardList", cardList);
		model.addAttribute("page", page);
		model.addAttribute("pm", pm);
		model.addAttribute("user", user);
		model.addAttribute("recommendCount", recommendCount);
		model.addAttribute("tradeCount", tradeCount);
		return "template/layout";
	}
	
	/***
	 * Get product list posted by user
	 * @param userLoginId
	 * @param page
	 * @param completed
	 * @param model
	 * @return
	 */
	@GetMapping("/user-product-list")
	public String userProductList(
			@RequestParam("userLoginId") String userLoginId,
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "completed", required = false) boolean completed,
			Model model) {

		if (page == null) {
			page = 1;
		}
		
		User user = userBO.getUserByLoginId(userLoginId);

		int totalCount = productBO.getProductListByOwnerIdOrKeyword(user.getId(), null, 0, null, true).size();

		Criteria cri = new Criteria();
		cri.setPage(page);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(userLoginId, null, (int)page, cri, completed);
		
		model.addAttribute("cardList", cardList);
		model.addAttribute("page", page);
		model.addAttribute("pm", pm);
		model.addAttribute("user", user);
		return "user/userProductList";
	}
	
	/***
	 * Go to chat view with the latest message arrived
	 * if no chat exists just return view name
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/chat")
	public String chatList(Model model, HttpSession session) {
		
		// get latest chat list
		ChatList latestChatList = chatListBO.getLatestChatListByUserId((Integer)session.getAttribute("userId"));
		if (latestChatList == null) { // if null just return view name
			model.addAttribute("viewName", "chat/chatList");
			model.addAttribute("title", "채팅 / ");
			return "template/layout";
		}
		
		// if there is chat list, redirect to the latest
		return "redirect:chat/" + latestChatList.getId();
	}
	
	/***
	 * chat view & get chat list with messages
	 * @param chatListId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/chat/{chatListId}")
	public String chatList(
			@PathVariable("chatListId") Integer chatListId,
			HttpSession session,
			Model model) {
		
		// get user id to check if logined or not
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		// get chat list which user has
		List<ChatListCard> clcList = mainBO.getChatListCardList(userId);
		
		// get chat card (has chat message list in it)
		ChatCard cc = mainBO.getChatCard(chatListId);
		
		model.addAttribute("viewName", "chat/chatList");
		model.addAttribute("title", "채팅 / ");
		model.addAttribute("chatListCardList", clcList);
		model.addAttribute("chatListId", chatListId);
		model.addAttribute("chatCard", cc);
		return "template/layout";
	}
	
	/***
	 * My page view, default info page
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/my-page")
	public String myPage(HttpSession session, Model model) {
		
		// check login
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		// find user to get profile image
		User user = userBO.getUserById(userId);
		String userProfileImagePath = user.getProfileImagePath();
		
		model.addAttribute("viewName", "myPage/myPage");
		model.addAttribute("title", "마이페이지 / ");
		model.addAttribute("userProfileImagePath", userProfileImagePath);
		return "template/layout";
	}
	
	/***
	 * Find id view
	 * @param model
	 * @return
	 */
	@GetMapping("/find-id")
	public String findIdView(Model model) {
		model.addAttribute("viewName", "user/findId");
		model.addAttribute("title", "아이디 찾기 / ");
		return "template/layout";
	}
	
	/***
	 * Find password view
	 * @param model
	 * @return
	 */
	@GetMapping("/find-pw")
	public String findPwView(Model model) {
		model.addAttribute("viewName", "user/findPw");
		model.addAttribute("title", "비밀번호 찾기 / ");
		return "template/layout";
	}
	
	
	
	

	/***
	 * chat view & get chat list with messages
	 * @param chatListId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/ws-chat/{chatListId}")
	public String wsChatList(
			@PathVariable("chatListId") Integer chatListId,
			HttpSession session,
			Model model) {
		
		// get user id to check if logined or not
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		// get chat list which user has
		List<ChatListCard> clcList = mainBO.getChatListCardList(userId);
		
		// get chat card (has chat message list in it)
		ChatCard cc = mainBO.getChatCard(chatListId);
		
		model.addAttribute("viewName", "chat/chatList");
		model.addAttribute("title", "채팅 / ");
		model.addAttribute("chatListCardList", clcList);
		model.addAttribute("chatListId", chatListId);
		model.addAttribute("chatCard", cc);
		return "template/layout";
	}
}
