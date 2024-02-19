package com.ms.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ms.aop.TimeTrace;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@TimeTrace
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
		session.removeAttribute("userNickame");
		
		// redirect to login view
		return "redirect:/home";
	}
	
}
