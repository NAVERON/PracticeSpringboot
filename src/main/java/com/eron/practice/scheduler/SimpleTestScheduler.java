package com.eron.practice.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component 
public class SimpleTestScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleTestScheduler.class);
	
	@Resource 
	private KafkaTemplate<String, String> kafkaTemplate;

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
	public void testSchedule() {
		log.info("test scheduler annotation, 生成随机的用户, 提供测试用例");
		
		log.info("kafka testing");
		
		kafkaTemplate.send("topic", "message");
	}
	
	// 监听kafka队列 
	@KafkaListener(groupId = "xxx", topics = "topic")
	public void kafkaListener(String message) {
		log.info("接收的kafka信息 : {}", message);
	}
	
	
}










