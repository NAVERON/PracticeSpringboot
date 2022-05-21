package com.eron.practice.config.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;



@Configuration 
public class ZookeeperWatcher implements Watcher {  // zookeeper节点的变化会被触发 
	
	private static final Logger log = LoggerFactory.getLogger(ZookeeperWatcher.class);

	@Override 
	public void process(WatchedEvent event) { 
		log.info("zookeeper server watcher implement \nevent type -> {}, watcher path -> {}", 
				event.getType(), event.getPath());
		
		if(event.getType() == EventType.NodeDeleted) {
		    log.info("节点发生删除事件 -> {}", event.getPath());
		}else if(event.getType() == EventType.NodeDataChanged) {
		    log.info("节点数据发生改变 -> {} ||=> {}", event.getPath());
		}else if(event.getType() == EventType.NodeChildrenChanged) {
			log.info("节点的孩子有变化 -> {} ||=> {}", event.getPath(), event.getState());
		}
		
		
	}

}








