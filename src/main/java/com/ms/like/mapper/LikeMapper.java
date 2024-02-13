package com.ms.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
	
	public void insertLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	public int selectLikeCountBySubjectIdUserIdType(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	public int selectLikeCountBySubjectIdType(
			@Param("subjectId") int subjectId,
			@Param("type") String type);
	
	public void deleteLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
}
