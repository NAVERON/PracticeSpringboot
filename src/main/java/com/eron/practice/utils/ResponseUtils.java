package com.eron.practice.utils;

import com.eron.practice.model.ResponseEntity;
import com.eron.practice.model.constant.ResponseCodeEnum;




public class ResponseUtils {
	
	public static ResponseEntity<Object> success(Object obj) {
		ResponseEntity<Object> response = new ResponseEntity<Object>();
		response.setCode(ResponseCodeEnum.SUCESS.getCode());
		response.setMessage(ResponseCodeEnum.SUCESS.getMessage());
		response.setData(obj);
		
		return response;
	}
	
	public static ResponseEntity<Object> success() {
		return ResponseUtils.success(null);
	}
	
	public static ResponseEntity<Object> error(ResponseCodeEnum responseCodeEnum) {
		ResponseEntity<Object> response = new ResponseEntity<Object>();
		response.setCode(responseCodeEnum.getCode());
		response.setMessage(responseCodeEnum.getMessage());
		
		return response;
	}

}










