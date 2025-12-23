package com.eron.practice.config.aop;

import javax.annotation.Resource;

import org.apache.ibatis.javassist.expr.NewArray;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eron.practice.model.CacheStore;
import com.google.common.cache.Cache;

@Component 
@Aspect 
public class CustomeAnnotationAspect {
	
	private static final Logger log = LoggerFactory.getLogger(CustomeAnnotationAspect.class);
	
	@Resource
	private CacheStore<String> simpleStringCache;
	
	@Pointcut(value = "")
	public void customeAnnotationPointcut() {}
	
	public Object customeAnnotationImpl(ProceedingJoinPoint proceesor) {
		
		// 将请求的url 保存cache 检查cache当前是否由缓存,  key 可以是id  sessionId , cache设置过期时间
		// 有 表示近期有请求, 直接拒绝执行方法
		// 如果cache没有 表示近期没有/ 请求时间很长了, 可以进入方法执行 
		
		return new Object();
	}
	
}









