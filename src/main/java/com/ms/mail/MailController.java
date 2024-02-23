package com.ms.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.ms.mail.bo.MailBO;
import com.ms.mail.domain.Mail;

@Controller
public class MailController {
	
	@Autowired
	private MailBO mailBO;
	
	@PostMapping("/mail")
	public void execMail(Mail mail) {
		mailBO.mailSend(mail);
	}
	
}
