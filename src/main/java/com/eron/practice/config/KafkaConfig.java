package com.eron.practice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class KafkaConfig { 

	// from config properties  
	private String topic1 = "testTopic";
	
	@Bean 
	public NewTopic myTopic() {
		return new NewTopic(topic1, 3, (short) 1);
	}
}
