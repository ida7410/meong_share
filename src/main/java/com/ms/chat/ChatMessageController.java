package com.ms.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ms.chat.bo.ChatMessageBO;
import com.ms.chat.domain.ChatMessage;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

@Controller
public class ChatMessageController {
	
	@Autowired
	private ChatMessageBO chatMessageBO;
	
	@Autowired
	private UserBO userBO;
	
	@MessageMapping("/wsChat/{chatListId}/send")
	@SendTo("/topic/chat/{chatListId}")
	public ChatMessage sendWS(
			@DestinationVariable Integer chatListId, 
			ChatMessage chatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
	    Integer userId = (Integer) headerAccessor.getSessionAttributes().get("userId");
	    if (chatListId == null || userId == null) {
	        return null;
	    }

	    User user = userBO.getUserById(userId);

	    chatMessage.setChatListId(chatListId);
	    chatMessage.setSenderId(userId);

	    if (chatMessage.getType().equals("image")) {
	        chatMessageBO.addChatMessage(chatListId, userId, chatMessage.getMessage(), user.getLoginId(), null, "image");
	    } 
	    else if (chatMessage.getType().equals("message")) {
	        chatMessageBO.addChatMessage(chatListId, userId, chatMessage.getMessage(), user.getLoginId(), null, "message");
	    }

	    return chatMessage;
	}

}
