package com.ms.mail.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailBO {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final String FROM_ADDRESS = "ida.yoonh741@gmail.com";
	
	/***
	 * Send mail
	 * @param address
	 * @param title
	 * @param content
	 */
	public void mailSend(String address, String title, String content) {
		
		SimpleMailMessage  message = new SimpleMailMessage();
		
		message.setFrom(FROM_ADDRESS);
		message.setTo(address);
		message.setSubject(title);
		message.setText(content);
		
		javaMailSender.send(message);
	}
	
}
