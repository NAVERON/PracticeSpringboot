package com.eron.practice.model;

public enum ResponseCodeEnum {
	
	SUCESS(200, "成功"),
	FAIL(-1, "失败"),
	ERROR_400(400, "请求错误"), 
	ERROR_404(404, "资源不存在"), 
	ERROR_500(500, "服务器异常");
	// 更多业务编码 

	private Integer code;
	private String message;
	
	ResponseCodeEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

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
	
	
}









