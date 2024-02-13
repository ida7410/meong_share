package com.ms.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	public void addLike(int subjectId, int userId, String type) {
		boolean isAlreadyLiked = likeMapper.selectLikeCountBySubjectIdUserIdType(subjectId, userId, type) > 0;
		
		if (isAlreadyLiked) {
			likeMapper.deleteLike(subjectId, userId, type);
		}
		else {
			likeMapper.insertLike(subjectId, userId, type);
		}
	}
	
	public int getLikeCountBySubjectIdType(int subjectId, String type) {
		return likeMapper.selectLikeCountBySubjectIdType(subjectId, type);
	}
	
}
