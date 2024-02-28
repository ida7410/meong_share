package com.ms.chat.chatMessage.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.chat.chatList.bo.ChatListBO;
import com.ms.chat.chatList.domain.ChatList;
import com.ms.chat.chatMessage.domain.ChatMessage;
import com.ms.chat.chatMessage.mapper.ChatMessageMapper;
import com.ms.common.FileManagerService;

@Service
public class ChatMessageBO {
	
	@Autowired
	private ChatMessageMapper chatMessageMapper;
	
	@Autowired
	private ChatListBO chatListBO;

    @Autowired
    private FileManagerService fileManagerService;
	
	public void addChatMessage(int chatListId, int userId, String message, String userLoginId, MultipartFile file, String type) {
		
		if (type.equals("image")) {
			String imagePath = fileManagerService.saveFile(userLoginId, file);
			chatMessageMapper.insertChatMessageImage(chatListId, userId, imagePath, type);
		}
		else {
			chatMessageMapper.insertChatMessage(chatListId, userId, message, type);
		}
		
		ChatList cl = chatListBO.getChatListById(chatListId);
		
		chatListBO.updateChatList(cl.getProductId(), cl.getOwnerId(), cl.getBuyerId());
	}

	public ChatMessage getLatestChatMessageByChatListId(int chatListId) {
		return chatMessageMapper.selectLatestChatMessageByChatListId(chatListId);
	}
	
	public List<ChatMessage> getChatMessageListByChatListId(int chatListId) {
		return chatMessageMapper.selectChatMessageListByChatListId(chatListId);
	}
	
}
