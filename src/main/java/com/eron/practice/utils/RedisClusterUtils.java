package com.eron.practice.utils;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.eron.practice.model.constant.RedisResultEnum;


@Component
public class RedisClusterUtils { 
	
	private static final Logger log = LoggerFactory.getLogger(RedisClusterUtils.class);
	
	// Redis 分布式锁的实现
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	// 需要实现缓存邮箱严重呢个马的工具功能, 不用再在其他地方使用, 有关redis的工具功能全部封装在这里 
	
	public static RedisResultEnum tryRedisLock() {
		// 返回String表达更多的锁状态和额外信息 
		return RedisResultEnum.LOCK_SUCCESS;
	}
	
	public static RedisResultEnum releaseRedisLock() {
		// 释放Redis分布式锁 
		return RedisResultEnum.RELEASE_SUCESS;
	}
	
}









