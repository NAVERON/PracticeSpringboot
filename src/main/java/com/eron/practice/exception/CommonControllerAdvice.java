package com.eron.practice.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eron.practice.model.ResponseCodeEnum;

// import org.springframework.web.bind.annotation.RestControllerAdvice;
// RestControllerAdvice = @ControllerAdvice + @ResponseBody  /// 可以统一返回的格式 不需要每个方法都写上 Responsebody 
@ControllerAdvice 
public class CommonControllerAdvice { //对controller增加统一的操作和处理 
	
	private static final Logger log = LoggerFactory.getLogger(CommonControllerAdvice.class);
	
	/*
	 * WebDataBinder 用来绑定请求参数到指定的属性编辑器 
	 */
	@InitBinder 
	public void customeInitBinder(WebDataBinder binder) {
		// 设置传入的采纳书绑定等设置  
		// binder.setDisallowedFields("name");  // 设置传入的属性不绑定值 
	} 
	
	@ModelAttribute(value = "custome") 
	public Map<String, String> customeModelAttribute(Model model) {
		Map<String, String> globalAttribute = new HashMap<String, String>();
		
		log.warn("customeModelAttribute globalAttribute : {}", globalAttribute);
		return globalAttribute;
	}

	/**
	 * 自定义Exception 统一放在顶层package中 // 可以继承 Runtimexception  IOException 不同的分类 
	 * 自定义一场抛出可以定义返回的视图   modelAndView 
	 * 可以返回 自定义的错误详细信息 
	 * 所有的 Exception 都会走这个方法输出 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody   // json 格式化   RestControllerAdvice = ControllerAdvice + RespnseBody
	public Map<String, String> customeExceptionHandler(Exception ex){
		log.error("customeExceptionHandler --");
		
		Map<String, String> exceptionInfo = new HashMap<>();
		exceptionInfo.put("code", ResponseCodeEnum.FAIL.getCode().toString());
		exceptionInfo.put("message", ResponseCodeEnum.FAIL.getMessage());
		
		log.error("Custome Exception error : {}", ex);
		
		return exceptionInfo;
	}
	
	@ExceptionHandler(value = RuntimeException.class)
	public ModelAndView customeRuntimeError(RuntimeException ex) {  // just for test 
		ModelAndView modelView = new ModelAndView();
		
		Map<String, String> exceptionInfo = new HashMap<>();
		exceptionInfo.put("code", ResponseCodeEnum.FAIL.getCode().toString());
		exceptionInfo.put("message", ResponseCodeEnum.FAIL.getMessage());
		
		modelView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		modelView.setViewName("404");
		modelView.addObject(exceptionInfo);
		
		log.error("customeRuntimeError ERROR: {}", ex);
		
		return modelView;
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseBody
	public Map<String, String> customeIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, String> exceptionInfo = new HashMap<>();
		exceptionInfo.put("code", ResponseCodeEnum.PARAM_ERROR.getCode().toString());
		exceptionInfo.put("message", ResponseCodeEnum.PARAM_ERROR.getMessage());
		
		log.error("Custome Exception error : {}\n, cause: {}", ex.getMessage(), ex.getCause());
		
		return exceptionInfo;
	}
	
}









