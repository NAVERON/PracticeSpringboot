package com.eron.practice.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

//@Component 
public class MutiTaskScheduleConfig implements SchedulingConfigurer {
	
	private static final Logger log = LoggerFactory.getLogger(MutiTaskScheduleConfig.class);
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);
		
		CronTask cron = new CronTask(() -> {
			log.info("create cron task, running");
		}, "* 1 * * * *");
		IntervalTask fixed = new IntervalTask(() -> {
			log.info("create fixed task, running");
		}, 10 * 1000);
		IntervalTask delay = new IntervalTask(() -> {
			log.info("create delayed task, running");
		}, 13 * 1000);
		
		taskRegistrar.addCronTask(cron);
		taskRegistrar.addFixedRateTask(fixed);
		taskRegistrar.addFixedDelayTask(delay);
	}

}









