package com.ms.interceptor;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class PermissionInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object Handler) throws IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer)session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("/log-in");
            return false;
        }
        
        return true;
    }

}