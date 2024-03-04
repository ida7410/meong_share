package com.ms.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.chat.bo.ChatListBO;
import com.ms.chat.domain.ChatList;
import com.ms.mail.bo.MailBO;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat/chatList")
@RestController
public class ChatListRestController {
	
	@Autowired
	private ChatListBO chatListBO;
	
	@Autowired
	private MailBO mailBO;
	
	@Autowired
	private UserBO userBO;
	
	// ------- CREATE -------
	
	/***
	 * Create a chat list
	 * @param productId
	 * @param ownerId
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> createChat(
			@RequestParam("productId") int productId,
			@RequestParam("ownerId") int ownerId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// Check login status
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) { // if not
			result.put("code", 300);
			result.put("error_message", "세션이 만료되었습니다. 다시 로그인해주세요.");
			return result;
		}
		
		// check if the chat list already exists
		ChatList cl = chatListBO.getChatListByProductIdOwnderIdBuyerId(productId, ownerId, userId);
		
		if (cl == null) { // if no chat exists DB insert
			
			// create a chat
			int chatListId = chatListBO.addChatList(productId, ownerId, userId);
			
			// find the owner and send them email
			User user = userBO.getUserById(ownerId);
			mailBO.mailSend(user.getEmail(), 
						"[MEONG SHAR] 문의 알림", 
						"멍셰어 알림: 올리신 상품에 대한 문의가 있습니다! 지금 로그인해서 확인해보세요!");
			result.put("chatListId", chatListId);
		}
		else { // if already exists
			result.put("chatListId", cl.getId());
		}
		
		result.put("code", 200);
		result.put("result", "success");
		return result;
	}
	
}
