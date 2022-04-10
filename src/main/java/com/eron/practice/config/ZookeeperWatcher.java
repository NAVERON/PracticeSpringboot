package com.eron.practice.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;




@Configuration 
public class ZookeeperWatcher implements Watcher { 
	
	private static final Logger log = LoggerFactory.getLogger(ZookeeperWatcher.class);

	@Override 
	public void process(WatchedEvent event) { 
		log.info("zookeeper server watcher implement \nevent type -> {}, watcher path -> {}", 
				event.getType(), event.getPath());
		
	}

}








