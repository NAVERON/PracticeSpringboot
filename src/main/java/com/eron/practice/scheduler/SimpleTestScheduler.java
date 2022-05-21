package com.eron.practice.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component 
public class SimpleTestScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleTestScheduler.class);

    /** cron expression 
	    ┌───────────── second (0-59)
	    │ ┌───────────── minute (0 - 59)
	    │ │ ┌───────────── hour (0 - 23)
	    │ │ │ ┌───────────── day of the month (1 - 31)
	    │ │ │ │ ┌───────────── month (1 - 12) (or JAN-DEC)
	    │ │ │ │ │ ┌───────────── day of the week (0 - 7)
	    │ │ │ │ │ │          (0 or 7 is Sunday, or MON-SUN)
	    │ │ │ │ │ │
	    * * * * * *
    */
	@Scheduled(fixedDelay = 60 * 1000) 
	public void placeHolder() {
	    log.info("定时任务");
	}
	

	
	
}










