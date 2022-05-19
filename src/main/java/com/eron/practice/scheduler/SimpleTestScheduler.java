package com.eron.practice.scheduler;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component 
public class SimpleTestScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleTestScheduler.class);
	
	// 暂时用作kafka测试 
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
	public void kafkaProductorPush() {  // kafka生产者 
		
		// Kafka测试 
		log.info("test scheduler annotation, 生成随机的用户, 提供测试用例");
		
		log.info("kafka testing");
		Object test = new Object();
		
		// 普通方法处理发送结果 
		try {
			SendResult<String, String> result = kafkaTemplate.send("topic", (String) test).get();
			
			if(result.getRecordMetadata() != null) {
				log.info("发送成功");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		// 推荐方法处理kafka消息发送结果状态检查 
		ListenableFuture<SendResult<String, String>> futureResult = kafkaTemplate.send("topic", (String) test);
		futureResult.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onFailure(Throwable ex) {
				log.error("发送失败");
			}

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("发送成功");
			}
			
		});
		
	}
	
	// 监听kafka队列 
	@KafkaListener(groupId = "xxx", topics = "topic")  // kafka 消费端 groupud消费组概念 topic 主题
	public void kafkaCustomeDeal(ConsumerRecord<String, String> consumer) {  // 继承实现自定义消息结构 
		log.info("接收的kafka信息 : {}", consumer.value());
	}
	
	
}










