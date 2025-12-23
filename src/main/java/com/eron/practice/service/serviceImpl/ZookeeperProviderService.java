package com.eron.practice.service.serviceImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eron.practice.utils.ZookeeperUtils;

/**
 * zookeeper 对外提供功能化中间层
 * @author eron
 */

@Service
public class ZookeeperProviderService {

    private static final Logger log = LoggerFactory.getLogger(ZookeeperProviderService.class);
    
    @Resource 
    private ZookeeperUtils zookeeperClient;

    @PostConstruct
    public void init() {
        // 创建zookeeper锁的实现 等功能基础初始化

    }

    public void test() {
        // 測試watcher 
        Watcher forTest = new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                log.info("处理forTest Watcher事件");
            }
        };
        
        this.zookeeperClient.registZNodeWatcher("", forTest);
    }

}










