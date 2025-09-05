package com.ms.chat.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.chat.domain.ChatList;
import com.ms.chat.domain.ChatMessage;
import com.ms.chat.mapper.ChatMessageMapper;
import com.ms.common.FileManagerService;

@Service
public class ChatMessageBO {
	
	@Autowired
	private ChatMessageMapper chatMessageMapper;
	
	@Autowired
	private ChatListBO chatListBO;

    @Autowired
    private FileManagerService fileManagerService;
	
    // ------- CREATE -------
    
    /***
     * Created chat message
     * @param chatListId
     * @param userId
     * @param message
     * @param userLoginId
     * @param file
     * @param type
     */
	public void addChatMessage(int chatListId, int userId, String message, String userLoginId, MultipartFile file, String type) {
		
		if (type.equals("image")) { // if type is image
			String imagePath = fileManagerService.saveFile(file, userLoginId, type);
			chatMessageMapper.insertChatMessageImage(chatListId, userId, imagePath, type);
		}
		else if (type.equals("message")) { // if type is just text message
			chatMessageMapper.insertChatMessage(chatListId, userId, message, type);
		}
		else { // if type is end trade request
			chatMessageMapper.insertChatMessage(chatListId, userId, "", type);
		}
		
		// find chat list
		ChatList cl = chatListBO.getChatListById(chatListId);
		
		// update chat list to now()
		chatListBO.updateChatList(cl.getProductId(), cl.getOwnerId(), cl.getBuyerId());
	}
	
	
	// ------- READ -------
	
	/***
	 * Get the last message in a specific chat list
	 * @param chatListId
	 * @return
	 */
	public ChatMessage getLatestChatMessageByChatListId(int chatListId) {
		return chatMessageMapper.selectLatestChatMessageByChatListId(chatListId);
	}
	
	/***
	 * Get the list of chat message in a chat list
	 * @param chatListId
	 * @return
	 */
	public List<ChatMessage> getChatMessageListByChatListId(int chatListId) {
		return chatMessageMapper.selectChatMessageListByChatListId(chatListId);
	}
	
}
