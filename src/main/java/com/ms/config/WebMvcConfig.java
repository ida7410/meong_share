package com.ms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ms.common.FileManagerService;
import com.ms.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    
    @Autowired
    private PermissionInterceptor interceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
        .addInterceptor(interceptor)
        .addPathPatterns("/add-product")
        .addPathPatterns("/chat")
        .addPathPatterns("/my-page")
        ;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("/images/**")
        .addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);
    }
    
}