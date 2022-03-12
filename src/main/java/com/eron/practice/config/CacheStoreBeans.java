package com.eron.practice.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.eron.practice.model.CacheStore;
import com.eron.practice.model.User;

@Configuration
public class CacheStoreBeans {

	@Bean(value = "simpleStringCache")  // 或者name = "testCache" alias
	@Lazy(value = true) 
	public CacheStore<String> simpleStringCache() {
		return new CacheStore<>(100, TimeUnit.SECONDS);
	}
	
	/**
	 * 登录用户 缓存, 不用多次查询了, 另外可以根据是否有缓存判断是否已经登陆 
	 * @return
	 */
	@Bean(value = "loginUserCache")
	@Lazy(value = true)
	public CacheStore<User> loginUserCache() {
		return new CacheStore<>(10, TimeUnit.MINUTES);
	}
	
}





