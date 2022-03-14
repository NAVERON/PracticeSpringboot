package com.eron.practice.utils;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.eron.practice.model.constant.CommonConstant;
import com.eron.practice.model.constant.RedisResultEnum;


@Component
public class RedisClusterUtils { 
	
	// 需要实现缓存邮箱严重呢个马的工具功能, 不用再在其他地方使用, 有关redis的工具功能全部封装在这里 
	
	private static final Logger log = LoggerFactory.getLogger(RedisClusterUtils.class);
	
	@Resource 
	private StringRedisTemplate stringRedisTemplate;
	
	// redis 缓存注册验证码
	public String setRedisEmailVerifyCode(String email, String verifyCode, int expiryDuration, TimeUnit timeUnit) {
		Boolean status = stringRedisTemplate.opsForValue().setIfPresent(CommonConstant.REDIS_VERIFY_PREFIX + email, verifyCode, expiryDuration, timeUnit);
				
		if(!status) {
			return CommonConstant.REDIS_EMAIL_VERIFY_ERROR_PREFIX + verifyCode;
		}
		
		return verifyCode;
	}
	// redis获取注册验证码
	public Boolean checkRedisEmailVerifyCode(String email, String checkVerifyCode) {
		
		String key = CommonConstant.REDIS_VERIFY_PREFIX + email;
		String verifyCode = stringRedisTemplate.opsForValue().get(key);
		
		if(verifyCode.equals(checkVerifyCode)) {
			// 验证成功, 删除验证码
			stringRedisTemplate.delete(key);
			
			return true;
		}
		
		return false;
	}
	
	// Redis 分布式锁的实现  加锁解锁归一化 = 加锁和解锁都是同一个线程操作 
	
	private ThreadLocal<String> threadLock = new ThreadLocal<>();  // 安端是否同一个线程操作
	private ThreadLocal<Integer> lockTimes = new ThreadLocal<>();  // 加锁次数 可重入
	private ThreadLocal<Integer> retryLockTimes = new ThreadLocal<>() {
		@Override
		protected Integer initialValue() {
			return CommonConstant.REDIS_RETRYLOCK_TIMES;
		}
	};  // 自旋锁 重试次数 超过就跳出 
	
	public RedisResultEnum tryRedisLock(String key, int expiryDuration, TimeUnit timeUnit) {
		Boolean isLocked = false;
		if(threadLock.get() == null) {
			//还没有加锁
			String uuid = UUID.randomUUID().toString();  // 随机生成  解锁的时候获取当前线程的threadlocal 对比是否一致
			threadLock.set(uuid);
			
			isLocked = stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, expiryDuration, timeUnit);
			if(!isLocked) {
				// 没有加锁成功 内部循环  
				for(;;) {
					isLocked = stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, expiryDuration, timeUnit);
					if(isLocked || retryLockTimes.get() < 0) {
						break;
					}
					retryLockTimes.set(retryLockTimes.get() - 1);
				}
			}
		} else {
			isLocked = true;
		}
		// 可重入  累积重入次数 / 加锁的次数
		if(isLocked) {
			Integer count = lockTimes.get() == null ? 1 : lockTimes.get() + 1;
			lockTimes.set(count);
		}
		
		// 返回String表达更多的锁状态和额外信息 
		return isLocked ? RedisResultEnum.LOCK_SUCCESS : RedisResultEnum.LOCK_FAIL;
	}
	
	public RedisResultEnum releaseRedisLock(String key) {
		// 获取当前线程的threadLock 
		// 如果没有加锁直接解锁呢 ? 应当做成无影响 返回正常状态 
		String uuid = threadLock.get() == null ? "" : threadLock.get(); 
		if(uuid.equals(stringRedisTemplate.opsForValue().get(key))) {
			// 如果是当前线程 执行解锁  没有枷锁的情况下解锁 
			Integer count = lockTimes.get() == null ? -1 : lockTimes.get() - 1;
			if(count <= 0) { 
				// 当前线程锁全部解除  1 删除redis 2 删除threadLocal 各种临时变量 
				stringRedisTemplate.delete(key);
				
				threadLock.remove();
				lockTimes.remove();
				retryLockTimes.set(CommonConstant.REDIS_RETRYLOCK_TIMES);
			} else { 
				// 如果 多次加锁, 就需要多次释放锁 
				lockTimes.set(count);  // 加锁次数 - 1 
				retryLockTimes.set(CommonConstant.REDIS_RETRYLOCK_TIMES);  // 重置自自旋锁  次数
			}

			return RedisResultEnum.RELEASE_SUCESS;
		}
		// 释放Redis分布式锁  没有加过锁 = 释放失败, 不是当前线程的加锁释放 = 失败  
		return RedisResultEnum.RELEASE_FAIL;
	}
	
}









