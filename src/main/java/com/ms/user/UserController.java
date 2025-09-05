package com.ms.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ms.common.CookieManager;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private CookieManager cookieManager;
	
	/***
	 * Log in view
	 * @param model
	 * @return
	 */
//	@TimeTrace
	@GetMapping("/log-in")
	public String logIn(Model model) {
		model.addAttribute("viewName", "user/logIn");
		model.addAttribute("title", "Log-in / ");
		return "template/layout";
	}
	
	/***
	 * Sign up view
	 * @param model
	 * @return
	 */
	@GetMapping("/sign-up")
	public String signUp(Model model) {
		model.addAttribute("viewName", "user/signUp");
		model.addAttribute("title", "Sign Up / ");
		return "template/layout";
	}
	
	/***
	 * Log out
	 * @param session
	 * @return
	 */
	@GetMapping("/log-out")
	public String signOut(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) {
		// empty all session
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userNickame");
		
		// delete cookie
		Cookie c = cookieManager.getCookie(request, "productList");
		if (c != null) {
			c.setMaxAge(0);
			response.addCookie(c);
		}
		c = cookieManager.getCookie(request, "keywordList");
		if (c != null) {
			c.setMaxAge(0);
			response.addCookie(c);
		}
		
		// redirect to login view
		return "redirect:/log-in";
	}
	
}
