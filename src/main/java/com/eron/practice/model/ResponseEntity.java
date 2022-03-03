package com.eron.practice.model;

import java.io.Serializable;

public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = -8568987804372679659L; 
	
	private Integer code; // 状态码 
	private String message; // 状态信息 
	private T data; // 返回的数据主体 
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
