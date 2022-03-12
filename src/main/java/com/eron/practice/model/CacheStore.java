package com.eron.practice.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;

public class CacheStore<T> {
	
	private static final Logger log = LoggerFactory.getLogger(CacheStore.class);
	
	private Cache<String, T> cache;
	
	public CacheStore(int expiryDuration, TimeUnit timeUnit) {
		cache = CacheBuilder.newBuilder()
				.expireAfterAccess(expiryDuration, timeUnit)  // 给定时间没有读/写访问  定时回收
				.concurrencyLevel(Runtime.getRuntime().availableProcessors())  // 并发线程数 
				.initialCapacity(100)
				.maximumSize(500)  // 最大缓存量   超过按照LRU移除 
				.removalListener( cache -> {
					log.warn("remove cache key : {}, value : {}", cache.getKey(), cache.getValue());
				})
				.recordStats() // 生产环境应当去除 影响性能 
				.build();
	}
	
	public T get(String key) {
		return cache.getIfPresent(key);
	}
	
	public void set(String key, T value) {
		if(key != null && value != null) {
			cache.put(key, value);
			log.info("Record stored in {} Cache with key = {}", value.getClass().getSimpleName(), key);
		}
	}
	
	public Boolean del(String key) {
		if(key != null) {
			cache.invalidate(key);
			return true;
		}
		return false;
	}
	
	public Long size() {
		return cache.size();
	}
	
	/**
	 * 返回缓存的统计数据
	 * @return map 统计数据
	 */
	public Map<String, Object> cacheStats(){
		CacheStats stats = cache.stats();
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("hitRate", stats.hitRate());
		result.put("hitCount", stats.hitCount());
		result.put("averageLoadPenalty", stats.averageLoadPenalty());
		result.put("evictionCount", stats.evictionCount());
		
		return result;
	}
	
}










