package com.ms.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/my-page")
@Controller
public class MyPageController {
	
	@Autowired
	private ProductBO productBO;
	
	@GetMapping("/info")
	public String info() {
		return "myPage/info";
	}
	
	@GetMapping("/recent-trade")
	public String recentTrade(Model model, HttpSession session) {
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		List<Product> recentTradeProductList = productBO.getProductListByOwnerIdOrKeyword(userId, null, 0, 3, true);
		
		model.addAttribute("recentTradeProductList", recentTradeProductList);
		return "myPage/recentTrade";
	}
	
}
