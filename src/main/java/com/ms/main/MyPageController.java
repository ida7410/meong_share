package com.ms.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;
import com.ms.user.bo.UserBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/my-page")
@Controller
public class MyPageController {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private UserBO userBO;
	
	/***
	 * Info in MyPage
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/info")
	public String info(Model model, HttpSession session) {
		
		// check login status
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		String userProfileImagePath = userBO.getUserById(userId).getProfileImagePath();
		model.addAttribute("userProfileImagePath", userProfileImagePath);
		
		return "myPage/info";
	}

	// Update password view
	@GetMapping("/update-pw")
	public String updatePwView() {
		return "myPage/updatePw";
	}
	
	// Check recent trade the user made view
	@GetMapping("/recent-trade")
	public String recentTrade(Model model, HttpSession session) {
		
		// check login status
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		// find the list of recently traded product
		List<Product> recentTradeProductList = productBO.getProductListByOwnerIdOrKeyword(userId, null, 0, 3, true);
		
		model.addAttribute("recentTradeProductList", recentTradeProductList);
		return "myPage/recentTrade";
	}
	
	// Customer Serivice
	@GetMapping("/customer-service")
	public String customerService(Model model, HttpSession session) {
		
		// check login status
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/log-in";
		}
		
		String userEmail = userBO.getUserById(userId).getEmail();
		
		model.addAttribute("userEmail", userEmail);
		return "myPage/customerService";
	}
	
}
