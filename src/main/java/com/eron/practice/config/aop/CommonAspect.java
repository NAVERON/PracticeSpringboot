package com.eron.practice.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;



@Aspect 
@Component 
public class CommonAspect { 
	
	private static final Logger log = LoggerFactory.getLogger(CommonAspect.class);
	
	@Pointcut(value = "within ( com.eron.practice..* )")
	public void commonPoint() {
		log.warn("common aspect cut point define !!!");
	}
	
	@Around(value = "commonPoint()") 
 	public Object processCommonPoint(ProceedingJoinPoint processer) throws Throwable {
		MethodSignature signature = (MethodSignature) processer.getSignature();
		
		String className = signature.getDeclaringTypeName();
		String methodName = signature.getMethod().getName();
		
		StopWatch watcher = new StopWatch();
		watcher.start();
		Object result = processer.proceed();
		watcher.stop();
		
		log.warn("commmon processing spend print, {}.{} =--> {} ms.", className, methodName, watcher.getTotalTimeMillis());
		
		return result;
	}
	
	
}









