package com.eron.practice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eron.practice.model.RedisResultEnum;

public class RedisClusterLock { 
	
	private static final Logger log = LoggerFactory.getLogger(RedisClusterLock.class);
	
	// Redis 分布式锁的实现
	
	public static RedisResultEnum tryRedisLock() {
		// 返回String表达更多的锁状态和额外信息 
		return RedisResultEnum.LOCK_SUCCESS;
	}
	
	public static RedisResultEnum releaseRedisLock() {
		// 释放Redis分布式锁 
		return RedisResultEnum.RELEASE_SUCESS;
	}
	
}








