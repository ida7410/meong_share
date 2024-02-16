package com.ms.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.like.domain.Like;
import com.ms.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	public void addLike(int subjectId, int userId, String type) {
		boolean isAlreadyLiked = likeMapper.selectLikeCountBySubjectIdUserIdType(subjectId, userId, type) > 0;
		
		if (isAlreadyLiked) {
			likeMapper.deleteLike(subjectId, userId);
		}
		else {
			likeMapper.insertLike(subjectId, userId, type);
		}
	}
	
	public Like getLikeBySubjectIdUserIdType(int subjectId, int userId, String type) {
		Like like = likeMapper.selectLikeBySubjectIdUserIdType(subjectId, userId, type);
		return like;
	}
	
	public int getLikeCountBySubjectIdType(int subjectId) {
		return likeMapper.selectLikeCountBySubjectIdType(subjectId);
	}
	
	public int getRecommendCountBySubjectIdType(int subjectId) {
		return likeMapper.selectRecommendCountBySubjectIdType(subjectId);
	}
}
