package com.ms.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	public List<Post> getPostList() {
		return postMapper.selectPostList();
	}
	
}
