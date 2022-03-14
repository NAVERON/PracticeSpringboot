package com.eron.practice.model.constant;

public class CommonConstant {
	
	// redis设置邮箱验证码的 错误前缀
	public static String REDIS_EMAIL_VERIFY_ERROR_PREFIX = "REDIS_SET_VERIFYCODE_ERROR:";
	
	// 存放公共的静态常量 
	public static String REDIS_VERIFY_PREFIX = "REDIS_VERIFY:";
	
	// 用户登录缓存key前缀
	public static String CACHE_USER_PREFIX = "USER_LOGIN:";
	
	public static Integer REDIS_RETRYLOCK_TIMES = 10;
}
