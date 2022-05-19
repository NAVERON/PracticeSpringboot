package com.eron.practice.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eron.practice.config.interceptor.AuthoritationInterceptor;

public class WebBasicConfic implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        WebMvcConfigurer.super.addInterceptors(registry);
        
        registry.addInterceptor(new AuthoritationInterceptor()).addPathPatterns("/**");  // 注册认证拦截器 
        
    }

    
}
