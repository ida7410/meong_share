package com.ms.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.like.domain.Like;
import com.ms.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	// ------- CREATE & DELETE -------
	
	/***
	 * Like Toggle
	 * @param subjectId
	 * @param userId
	 * @param type
	 */
	public void likeToggle(int subjectId, int userId, String type) {
		
		// check if like already exists
		boolean isAlreadyLiked = likeMapper.selectLikeCountBySubjectIdUserIdType(subjectId, userId, type) > 0;
		
		if (isAlreadyLiked) { // if yes delete it
			likeMapper.deleteLike(subjectId, userId);
		}
		else { // if not delete it
			likeMapper.insertLike(subjectId, userId, type);
		}
	}
	

	// ------- READ -------
	
	/***
	 * Get like by subject id, user id, and type
	 * @param subjectId
	 * @param userId
	 * @param type
	 * @return
	 */
	public Like getLikeBySubjectIdUserIdType(int subjectId, int userId, String type) {
		Like like = likeMapper.selectLikeBySubjectIdUserIdType(subjectId, userId, type);
		return like;
	}
	
	/***
	 * Get the number of like where subject id and type match
	 * @param subjectId
	 * @return
	 */
	public int getLikeCountBySubjectIdType(int subjectId) {
		return likeMapper.selectLikeCountBySubjectIdType(subjectId);
	}
	
	/***
	 * Get the number of like where subject id and type match
	 * @param subjectId
	 * @return
	 */
	public int getRecommendCountBySubjectIdType(int subjectId) {
		return likeMapper.selectRecommendCountBySubjectIdType(subjectId);
	}
	
}
