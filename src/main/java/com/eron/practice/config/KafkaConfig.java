package com.eron.practice.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;

import com.eron.practice.service.KafkaProviderService;

/**
 * kafka 配置topic
 * 设置producter consumer工厂类等 
 * @author eron
 *
 */
@Configuration 
public class KafkaConfig {
    
    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);
    
    @Bean(name = "kafkaadmin")
    public KafkaAdmin kafkaAdminConfig(KafkaProperties kafkaProperties) {
        KafkaAdmin admin = new KafkaAdmin(kafkaProperties.buildAdminProperties());
        admin.setFatalIfBrokerNotAvailable(true);  // 如果kafka broker不可用不影响spring上下文
        
        // 一种提前创建topic的方式 
        AdminClient client = AdminClient.create(kafkaProperties.buildAdminProperties());
        if(client != null) {
            Collection<NewTopic> topics = new ArrayList<>();
            topics.add(new NewTopic("xxx", 5, (short) 1));
            client.createTopics(topics);
        }
        
        return admin;
    }
    
    @Bean(name = "testTopic")
    public NewTopic myTopic() {
        return new NewTopic("testTopic", 3, (short) 1);  // topic名称 分区数 复制因子
    }

}








