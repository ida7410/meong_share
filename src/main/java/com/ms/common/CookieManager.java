package com.ms.common;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;

@Component
public class CookieManager {
	
	public List<String> getListByCookie(Cookie cookie) {

		// keyword,keyword,keyword,...의 형태를 리스트로
		List<String> keywordList = new ArrayList<>();
		// list로 변환
		if (cookie != null) {
			String keywordCookieString = URLDecoder.decode(cookie.getValue()); // 존재한다면 value는 keyword,keyword,keyword,...의 형태
			String[] keywordArray = keywordCookieString.split(","); // array 형태로 변환
			keywordList.addAll(Arrays.asList(keywordArray)); // array를 리스트로
		}
		
		return keywordList;
	}

	
	
}
