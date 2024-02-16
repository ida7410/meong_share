package com.ms.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.like.domain.Like;

@Mapper
public interface LikeMapper {
	
	public void insertLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	public Like selectLikeBySubjectIdUserIdType(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	public int selectLikeCountBySubjectIdUserIdType(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	public int selectLikeCountBySubjectIdType(int subjectId);
	
	public int selectRecommendCountBySubjectIdType(int subjectId);
	
	public void deleteLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId);
	
}
