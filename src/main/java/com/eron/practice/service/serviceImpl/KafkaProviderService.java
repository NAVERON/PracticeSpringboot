package com.eron.practice.service.serviceImpl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.eron.practice.model.ShipTrackPoint;
import com.eron.practice.service.ShipTrackService;
import com.eron.practice.utils.JsonUtils;

/**
 * kafka 生产内容服务
 * 对上层提供消息发送服务 
 * @author eron
 * 
 */
@Service
public class KafkaProviderService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProviderService.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    @Resource 
    private ShipTrackService shipTrackService;

    // 返回listenableFuture 可以在外层处理返回时的动作 定义ListenableFutureCallback 和实现方法
    public ListenableFuture<SendResult<String, String>> test() { // kafka生产者
        // Kafka测试
        log.info("kafka testing");
        String test = "hello world!!!";
        // 普通方法处理发送结果
        try {
            SendResult<String, String> result = kafkaTemplate.send("topic", test).get();
            if (result.getRecordMetadata() != null) {
                log.info("发送成功");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 推荐方法处理kafka消息发送结果状态检查
        ListenableFuture<SendResult<String, String>> futureResult = kafkaTemplate.send("topic", test);
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

        return futureResult;
    }

    
    // 对读取的json对象反序列化转换为路径点存储到数据库中 
    public void saveShipTrackPoint(String track) {
        // json -> track object, save to db 
        ShipTrackPoint shipTrackPoint = JsonUtils.fromJsonString(track, ShipTrackPoint.class);
        log.info("从队列中获取到轨迹点 --> {}", shipTrackPoint.toString());
        
        // 所以一般使用队列中间件的时候会在原始数据上封装一层 分层利于灵活修改
        shipTrackService.addTrackPointsToShip(shipTrackPoint.getShipId(), shipTrackPoint);
    }
    
    // 监听kafka队列 groupid = 消費群id 
    @KafkaListener(groupId = "xxx", topics = "tracks") // kafka 消费端 groupud消费组概念 topic 主题
    public void kafkaCustomeDeal(String track) { // 继承实现自定义消息结构
        log.info("接收tracks队列数据----------------------");
        this.saveShipTrackPoint(track);
        
    }
}









