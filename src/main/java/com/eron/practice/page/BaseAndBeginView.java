package com.eron.practice.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class BaseAndBeginView { 

	private static final Logger log = LoggerFactory.getLogger(BaseAndBeginView.class);
	
	@GetMapping(value = "")
	public String baseHome() {
		return "index";
	}
	
	@GetMapping(value = "home")
	public String home() {
		return "redirect:";  // 重定向后使用名称是url的后缀  所以不能是模板名称 index 
	}
	
	@GetMapping(value = "about")
	public String about() {
		return "about";
	}
	
	@GetMapping(value = "404")
	public String notFound() {
		return "404";
	}
	
	
}









