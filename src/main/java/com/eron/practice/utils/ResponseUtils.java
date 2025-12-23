package com.eron.practice.utils;

import com.eron.practice.model.BusinessResponseEntity;
import com.eron.practice.model.constant.ResponseCodeEnum;




public class ResponseUtils {
	
	public static BusinessResponseEntity<Object> success(Object obj) {
		BusinessResponseEntity<Object> response = new BusinessResponseEntity<Object>();
		response.setCode(ResponseCodeEnum.SUCESS.getCode());
		response.setMessage(ResponseCodeEnum.SUCESS.getMessage());
		response.setData(obj);
		
		return response;
	}
	
	public static BusinessResponseEntity<Object> success() {
		return ResponseUtils.success(null);
	}
	
	public static BusinessResponseEntity<Object> error(ResponseCodeEnum responseCodeEnum) {
		BusinessResponseEntity<Object> response = new BusinessResponseEntity<Object>();
		response.setCode(responseCodeEnum.getCode());
		response.setMessage(responseCodeEnum.getMessage());
		
		return response;
	}

}










