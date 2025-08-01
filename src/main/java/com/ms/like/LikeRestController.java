package com.ms.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	@PostMapping("/like")
	public Map<String, Object> likeToggle(
			@RequestParam("subjectId") int subjectId,
			@RequestParam("type") String type,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 300);
			result.put("error_message", "Your session has been expired. Please try to log in again.");
			return result;
		}
		
		// DB insert
		likeBO.likeToggle(subjectId, userId, type);
		
		result.put("code", 200);
		result.put("result", "success");
		return result;
	}
	
}
