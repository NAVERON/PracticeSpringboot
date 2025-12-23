package com.eron.practice.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.eron.practice.model.CacheStore;
import com.eron.practice.model.User;

@Configuration
public class CacheStoreBeans {

	// 本地缓存可以直接使用caffein 缓存的实现， guava 缓存也可以
	// 实际使用时 可以不在这里做一个bean对象， 直接使用工具静态方法， 实际使用的类中初始化和使用
	// 全局缓存则使用redis  mermaid 缓存框架

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
	public CacheStore<User> loginUserCache() {  // 使用bean默认为方法名称  Bean 属性value可以自定义bean调用名称 
		return new CacheStore<>(10, TimeUnit.MINUTES);
	}
	
}





