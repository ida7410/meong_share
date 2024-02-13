package com.ms.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/my-page")
@Controller
public class MyPageController {
	
	@GetMapping("/info")
	public String info() {
		return "myPage/info";
	}
	
	@GetMapping("/recent-trade")
	public String recentTrade() {
		return "myPage/recentTrade";
	}
	
}
