package com.ms.chat.chatList;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.chat.chatList.bo.ChatListBO;
import com.ms.chat.chatList.domain.ChatList;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat/chatList")
@RestController
public class ChatListRestController {
	
	@Autowired
	private ChatListBO chatListBO;
	
	@PostMapping("/create")
	public Map<String, Object> createChat(
			@RequestParam("productId") int productId,
			@RequestParam("ownerId") int ownerId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 300);
			result.put("error_message", "세션이 만료되었습니다. 다시 로그인해주세요.");
			return result;
		}
		
		// DB select
		ChatList cl = chatListBO.getChatListByProductIdOwnderIdBuyerId(productId, ownerId, userId);
		
		// if no chat exists DB insert
		if (cl == null) {
			int chatListId = chatListBO.addChatList(productId, ownerId, userId);
			result.put("chatListId", chatListId);
		}
		else {
			result.put("chatListId", cl.getId());
		}
		
		result.put("code", 200);
		result.put("result", "success");
		return result;
	}
	
}
