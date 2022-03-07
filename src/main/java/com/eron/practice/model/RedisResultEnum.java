package com.eron.practice.model;

import javax.management.loading.PrivateClassLoader;

public enum RedisResultEnum {
	
	LOCK_SUCCESS(Boolean.TRUE, "加锁成功"), 
	RELEASE_SUCESS(Boolean.TRUE, "释放锁成功");
	
	private Boolean status;
	private String message;
	
	RedisResultEnum(Boolean status, String message) {
		this.status = status;
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}









