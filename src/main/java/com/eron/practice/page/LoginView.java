package com.eron.practice.page;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eron.practice.model.ResponseEntity;
import com.eron.practice.model.User;
import com.eron.practice.service.UserService;
import com.eron.practice.utils.MailClientUtils;
import com.eron.practice.utils.RandomGeneratorUtils;
import com.eron.practice.utils.ResponseUtils;



@Controller 
@RequestMapping(value = "page/v1") 
public class LoginView {
	
	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	@Resource 
	private UserService userService;
	
	@GetMapping(value = "login")
	public ModelAndView login(ModelAndView modelAndView) {
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@PostMapping(value = "generateRandomEmailCode") 
	@ResponseBody 
	public ResponseEntity<Object> generateRandomEmailCode(@RequestParam(value = "email") String emailAddress) {
		// !!!!! 警示  post http请求中，如果后端使用requestParam 必须在url中拼接参数 
		// 发送验证码邮件 
		log.info("regist email generate random verifycation code. params : {}", emailAddress);
		String verifyCode = userService.verifyCodeProcess(emailAddress);
		log.info("generated verifyCode : {}", verifyCode); // 可以返回验证码 或 错误信息
		
		return ResponseUtils.success();
	}
	
	@PostMapping(value = "doLogin")
	public ModelAndView doLogin(ModelAndView modelAndView, RedirectAttributes redirectAttr, 
			@RequestParam(value = "username") String userName, 
			@RequestParam(value = "password") String password) {
		
		// 正确则构造user对象 
		log.info("doLogin --> [ nusername : {}, password : {} ]", userName, password);
		
		// 使用用户名称 可以是用户名/注册邮箱 
		User loginUser = userService.userLoginCheck(userName, password);
		log.info("登陆查询结果 : {}", loginUser);
		
		// 没有用户信息, 表示用户名/密码错误  或  用户没有注册 
		if(loginUser == null) {
			log.warn("没有查询到用户, 返回登陆界面");
			
			modelAndView.getModelMap().put("status", false);
			modelAndView.getModelMap().put("message", "用户登陆信息不正确或没有注册");
			
			modelAndView.setViewName("login");  // modelAndView setObject 是设置请求参数的 
		} else {
			log.info("查询到用户 : {}, 进入用户主页", loginUser);
			
			redirectAttr.addFlashAttribute("status", true);
			redirectAttr.addFlashAttribute("message", "用户查询成功");
			redirectAttr.addFlashAttribute("user", loginUser);
			
			modelAndView.setViewName("redirect:/page/v1/userHome");  // 默认foreard 重定向
		}
		
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
			@RequestParam(value = "registemail") String registEmail, 
			@RequestParam(value = "verifycation_code") String verifycationCode) {
		
		log.info("doRegidt, username : {}, password : {}, registEmail : {}, verifycationcode : {}", 
				userName, password, registEmail, verifycationCode);
		
		// 检验验证码 
		User registedUser = userService.checkOfRegistProcess(userName, password, registEmail, verifycationCode);
		// 注册成功 返回登陆界面  注册失败, 返回注册界面 
		if(registedUser == null) {
			modelAndView.setViewName("redirect:/page/v1/regist");
		} else {
			modelAndView.setViewName("redirect:/page/v1/login");
		}
		
		return modelAndView;  // 后面跟url  其余返回templates 模板html
	}
	
}










