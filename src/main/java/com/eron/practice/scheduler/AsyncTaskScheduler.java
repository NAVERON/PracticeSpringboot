package com.eron.practice.scheduler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component 
@EnableAsync 
public class AsyncTaskScheduler { 
	
	private static final Logger log = LoggerFactory.getLogger(AsyncTaskScheduler.class);

	@Scheduled(fixedDelay = 10 * 1000) 
	@Async 
	public void testAsync() {
		log.info("Async task testing ...");
	}
	
	//当前当作测试对象使用, 后期使用测试补充 
	
}









