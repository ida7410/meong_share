package com.ms.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("viewName", "main/main");
		return "template/layout";
	}
	
	@GetMapping("/search")
	public String search(
			@RequestParam(name = "keyword", required = false) String keyword,
			Model model) {
		model.addAttribute("viewName", "search/search");
		model.addAttribute("keyword", keyword);
		return "template/layout";
	}
	
}
