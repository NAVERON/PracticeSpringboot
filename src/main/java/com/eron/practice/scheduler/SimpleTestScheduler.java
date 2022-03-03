package com.eron.practice.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component 
public class SimpleTestScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleTestScheduler.class);

	@Scheduled(fixedDelay = 10 * 1000) 
	public void testSchedule() {
		log.info("test scheduler annotation");
	}
}





