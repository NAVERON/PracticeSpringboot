package com.eron.practice.model.constant;


public enum RedisResultEnum {
	
	// boolean + message --> boolean = final status, message = business things
	LOCK_SUCCESS(Boolean.TRUE, "加锁成功"), 
	LOCK_FAIL(Boolean.TRUE, "加锁失败"), 
	RELEASE_SUCESS(Boolean.TRUE, "释放锁成功"), 
	RELEASE_FAIL(Boolean.FALSE, "解锁失败");
	
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









