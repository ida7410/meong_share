package com.ms.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {
	
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/1")
	@ResponseBody
	public String test1() {
		return "Hello World!";
	}
	
	@GetMapping("/2")
	@ResponseBody
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("hello", "world!");
		return map;
	}
	
	@GetMapping("/3")
	public String test3() {
		return "test/test";
	}
	
	@GetMapping("/4")
	@ResponseBody
	public List<Post> test4() {
		List<Post> postList = postBO.getPostList();
		return postList;
	}
	
}
