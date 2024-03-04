package com.ms.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.chat.bo.ChatMessageBO;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat/chatMessage")
@RestController
public class ChatMessageRestController {
	
	@Autowired
	private ChatMessageBO chatMessageBO;
	
	@Autowired
	private UserBO userBO;
	
	// ------- CREATE -------
	
	/***
	 * Create chat message
	 * @param chatListId
	 * @param message
	 * @param chatImageFile
	 * @param type
	 * @param session
	 * @return
	 */
	@PostMapping("/send")
	public Map<String, Object> sendChatMessage(
			@RequestParam("chatListId") int chatListId,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "chatImageFile", required = false) MultipartFile chatImageFile,
			@RequestParam("type") String type,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// check login status
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 300);
			result.put("error_message", "세션이 만료되었습니다. 다시 로그인해주세요.");
		}
		
		User user = userBO.getUserById(userId);
		
		// create chat message
		chatMessageBO.addChatMessage(chatListId, userId, message, user.getLoginId(), chatImageFile, type);
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
}
