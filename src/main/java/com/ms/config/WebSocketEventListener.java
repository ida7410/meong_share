package com.ms.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ms.chat.domain.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations messageTemplate;
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		Integer userId = (int) accessor.getSessionAttributes().get("userId");
		if (userId != null) {
			log.info(userId + " has disconnected");
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setSenderId(userId);
			chatMessage.setType("leave");
			
			messageTemplate.convertAndSend(chatMessage);
		}
	}
	
}
