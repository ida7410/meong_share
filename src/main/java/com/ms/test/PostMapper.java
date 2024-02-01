package com.ms.test;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
	
	public List<Post> selectPostList();
	
}
