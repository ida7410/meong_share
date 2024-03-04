package com.ms.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.like.domain.Like;

@Mapper
public interface LikeMapper {
	
	// ------- CREATE -------
	
	/***
	 * Insert like
	 * @param subjectId
	 * @param userId
	 * @param type
	 */
	public void insertLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	
	// ------- READ -------
	
	/***
	 * Select like by subject id, user id, and type
	 * @param subjectId
	 * @param userId
	 * @param type
	 * @return
	 */
	public Like selectLikeBySubjectIdUserIdType(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	/***
	 * Select the number of like
	 * @param subjectId
	 * @param userId
	 * @param type
	 * @return
	 */
	public int selectLikeCountBySubjectIdUserIdType(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId,
			@Param("type") String type);
	
	/***
	 * Select the number of like by subject id and type
	 * @param subjectId
	 * @param type
	 * @return
	 */
	public int selectLikeCountBySubjectIdType(
			@Param("subjectId") int subjectId,
			@Param("type") String type);
	
	
	// ------- DELETE -------
	
	/***
	 * Remove Like
	 * @param subjectId
	 * @param userId
	 */
	public void deleteLike(
			@Param("subjectId") int subjectId,
			@Param("userId") int userId);
	
}
