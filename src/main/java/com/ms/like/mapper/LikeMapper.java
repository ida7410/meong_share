package com.ms.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
	
	public void insertLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	public int selectLikeCount(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	public void deleteLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
}
