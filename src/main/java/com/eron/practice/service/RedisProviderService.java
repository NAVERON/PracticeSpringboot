package com.eron.practice.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * redis 对外提供服务的中间层 
 * 对redis utils封装一层
 * @author eron
 *
 */
@Service 
public class RedisProviderService {

	private static final Logger log = LoggerFactory.getLogger(RedisProviderService.class);
	
	@PostConstruct 
	public void init() {
		// 基础配置初始化 
	}
	
}








