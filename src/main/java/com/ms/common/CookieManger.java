package com.ms.common;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieManger {
	
	public HttpServletResponse response;
	public HttpServletRequest request;
	
	public void setCookie(String id, String value) {
		Cookie cookie = new Cookie(id, value);
		response.addCookie(cookie);
	}
	
	public String getCookie(String id){
	    Cookie[] cookies = request.getCookies();
	    if(cookies != null){
	        for (Cookie cookie : cookies) {
	            String name = cookie.getName();
	            if (name.equals(id)) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null;
	}

	public List<String> setKeywordList(String keyword) {

		// 모든 쿠키 들고 오기
		Cookie[] cookies = request.getCookies();
		Cookie keywordCookie = null; // keywordList의 쿠키 
		// keywordList의 쿠키만 가져오기
		for (Cookie c : cookies) {
			if (c.getName().equals("keywordList")) {
				keywordCookie = c; // 존재한다면 value는 keyword,keyword,keyword,...의 형태
				break;
			}
		}

		// keyword,keyword,keyword,...의 형태를 리스트로
		List<String> keywordList = new ArrayList<>();
		// list로 변환
		if (keywordCookie != null) {
			String keywordCookieString = URLDecoder.decode(keywordCookie.getValue()); // 존재한다면 value는 keyword,keyword,keyword,...의 형태
			String[] keywordArray = keywordCookieString.split(","); // array 형태로 변환
			keywordList.addAll(Arrays.asList(keywordArray)); // array를 리스트로
		}
		
		// 단순 상품 검색 x keyword가 존재하고 리스트에 keyword가 없을 때
		if (keyword != null && !keywordList.contains(keyword)) {
			String keywordListString = null;
			
			if (keywordCookie != null) { // 존재한다면 value는 keyword,keyword,keyword,...의 형태
				keywordListString = URLDecoder.decode(keywordCookie.getValue()); // keyword,keyword,keyword,...
				keywordListString = keywordListString + "," + keyword; // ,keyword 추가
			}
			else {
				keywordListString = keyword; // 없다면 단순 keyword로
			}
			
			// 쿠키 추가 / 업데이트
			Cookie cookie = new Cookie("keywordList", URLEncoder.encode(keywordListString));
			cookie.setMaxAge(60);
			response.addCookie(cookie);
			keywordList.add(keyword);
		}
		
		Collections.reverse(keywordList);
		
		return keywordList;
	}
}
