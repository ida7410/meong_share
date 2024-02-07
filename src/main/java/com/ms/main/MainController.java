package com.ms.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.main.bo.MainBO;
import com.ms.main.domain.Card;
import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	private MainBO mainBO;
	
	@Autowired
	private ProductBO productBO;
	
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
			Model model) {
		
		// DB select
		List<Card> cardList = mainBO.getCardByKeyword(keyword);
		
		model.addAttribute("viewName", "product/productSearch");
		model.addAttribute("keyword", keyword);
		model.addAttribute("cardList", cardList);
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
	public String userInfo(Model model) {
		model.addAttribute("viewName", "user/userInfo");
		return "template/layout";
	}
	
	@GetMapping("/log-in")
	public String logIn(Model model) {
		model.addAttribute("viewName", "user/logIn");
		return "template/layout";
	}
	
	@GetMapping("/sign-up")
	public String signUp(Model model) {
		model.addAttribute("viewName", "user/signUp");
		return "template/layout";
	}
	
	@GetMapping("/log-out")
	public String signOut(HttpSession session) {
		// session의 내용을 모두 비운다.
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		// redirect to login view
		return "redirect:/home";
	}
}
