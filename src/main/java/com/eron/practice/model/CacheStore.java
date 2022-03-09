package com.eron.practice.model;

import java.util.concurrent.TimeUnit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheStore<T> {
	
	private static final Logger log = LoggerFactory.getLogger(CacheStore.class);
	
	private Cache<String, T> cache;
	
	public CacheStore(int expiryDuration, TimeUnit timeUnit) {
		cache = CacheBuilder.newBuilder().expireAfterWrite(expiryDuration, timeUnit)
				.concurrencyLevel(Runtime.getRuntime().availableProcessors())
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
	
}










