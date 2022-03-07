package com.eron.practice.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eron.practice.model.ResponseEntity;
import com.eron.practice.model.User;
import com.eron.practice.utils.ResponseUtils;



@Controller 
@RequestMapping(value = "page/v1") 
public class LoginView {
	
	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	@GetMapping(value = "login")
	public ModelAndView login(ModelAndView modelAndView) {
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@PostMapping(value = "generateRandomEmailCode") 
	@ResponseBody 
	public ResponseEntity<Object> generateRandomEmailCode(@RequestParam(value = "email") String email) {
		// 发送验证码邮件 
		log.info("regist email generate random verifycation code. params : {}", email);
		
		return ResponseUtils.success();
	}
	
	@PostMapping(value = "doLogin")
	public ModelAndView doLogin(ModelAndView modelAndView, @RequestParam(value = "username") String userName, 
			@RequestParam(value = "password") String password) {
		
		// 验证redis的随机码是否正确 
		// 正确则构造user对象 
		log.info("doLogin ...\nusername : {}, password : {}", userName, password);
		
		modelAndView.setViewName("redirect:userHome");
		// 登陆成功进入用户首页
		return modelAndView;
	}
	
	@GetMapping(value = "regist")
 	public ModelAndView regist(ModelAndView modelAndView) {
		modelAndView.setViewName("regist");
		return modelAndView;
	}
	
	@PostMapping(value = "doRegist")
	public ModelAndView doRegist(ModelAndView modelAndView, @RequestParam(value = "username") String userName, 
			@RequestParam(value = "password") String password, 
			@RequestParam(value = "regist_email") String registEmail, 
			@RequestParam(value = "verifycation_code") String verifycationCode) {
		
		log.info("doRegidt, username : {}, password : {}, registEmail : {}, verifycationcode : {}", 
				userName, password, registEmail, verifycationCode);
		
		// 构造用户对象 
		User registedUser = User.createBuilder().name(userName).password(password).registEmail(registEmail).build();
		log.info("registed User info : {}", registedUser);
		
		// 注册成功 返回登陆界面 
		// 注册失败, 返回注册界面 
		
		modelAndView.setViewName("redirect:/page/v1/login");
		
		return modelAndView;  // 后面跟url  其余返回templates 模板html
	}
	
}










