package com.ms.mail.bo;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.mail.domain.Mail;

@Service
public class MailBO {
	
	private JavaMailSender javaMailSender;
	private static final String FROM_ADDRESS = "ida.yoonh741@gmail.com";
	
	public void mailSend(Mail mail){
		SimpleMailMessage  message = new SimpleMailMessage();
		message.setTo(mail.getAddress());
		message.setSubject(mail.getTitle());
		message.setText(mail.getMessage());
		javaMailSender.send(message);
	}
	
}
