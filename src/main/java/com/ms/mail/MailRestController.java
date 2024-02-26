package com.ms.mail;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.mail.bo.MailBO;
import com.ms.user.bo.UserBO;

@RestController
public class MailRestController {
	
	@Autowired
	private MailBO mailBO;
	
	@PostMapping("/mail")
	public Map<String, Object> execMail(
			@RequestParam("address") String address,
			@RequestParam("title") String title,
			@RequestParam("content") String content) {
		
		mailBO.mailSend(address, title, content);
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		return result;
	}
	
}
