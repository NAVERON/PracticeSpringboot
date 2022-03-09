package com.eron.practice.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.eron.practice.model.CacheStore;

@Configuration
public class CacheStoreBeans {

	@Bean(value = "simpleStringCache")  // 或者name = "testCache" alias
	@Lazy(value = true) 
	public CacheStore<String> simpleStringCache(){
		return new CacheStore<>(100, TimeUnit.SECONDS);
	}
	
	// 自定义俄国中类型的cache bean 
	
}





