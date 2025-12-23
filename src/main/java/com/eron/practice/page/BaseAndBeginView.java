package com.eron.practice.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller 
@RequestMapping(value = "") 
public class BaseAndBeginView { 
	
	private static final Logger log = LoggerFactory.getLogger(BaseAndBeginView.class);
	
	@GetMapping(value = "") 
	public ModelAndView baseHome(ModelAndView modelAndView) {
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@GetMapping(value = "home")
	public ModelAndView home(ModelAndView modelAndView) {
		modelAndView.setViewName("forward:/");  // 重定向后面写  url地址, 不是html 模板
		return modelAndView;  
		// "redirect:/" 重定向后使用名称是url的后缀  所以不能是模板名称 index 
		// 重定向 RedirectAttributes attr = new RedirectAttributesModelMap();  使用重定向属性 添加重定向 参数 加在方法参数里面 
		// public ModelAndView home(ModelAndView modelAndView, RedirectAttributes attr) {attr.addAttribute(xx, xx)} 类似
		// 或者重定向自己实现  请求方法中使用 HttpServletRequest request, HttpServletResponse response 提取参数 
	}
	
	@GetMapping(value = "about")
	public ModelAndView about(ModelAndView modelAndView) {
		modelAndView.setViewName("about");
		return modelAndView;
	}
	
	@GetMapping(value = "404")
	public ModelAndView notFound(ModelAndView modelAndView) {
		modelAndView.setViewName("404");
		return modelAndView;
	}
	
	@GetMapping(value = "error")
	public ModelAndView error(ModelAndView modelAndView) {
		modelAndView.setViewName("error");
		return modelAndView;
	}
	
}









