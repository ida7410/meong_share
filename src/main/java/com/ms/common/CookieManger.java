package com.ms.common;

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
}
