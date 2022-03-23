package com.eron.practice.page;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eron.practice.model.CacheStore;
import com.eron.practice.model.ResponseEntity;
import com.eron.practice.model.User;
import com.eron.practice.service.UserService;
import com.eron.practice.utils.ResponseUtils;



@Controller 
@RequestMapping(value = "page/v1")   // path = "page/v1" 
public class LoginView {
	
	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	@Resource 
	private UserService userService;
	
	@GetMapping(value = "login")
	public ModelAndView login(ModelAndView modelAndView, 
			@RequestParam(value = "message", required = false) String message) {
		modelAndView.addObject("message", message);
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@PostMapping(value = "generateRandomEmailCode") 
	@ResponseBody 
	public ResponseEntity<Object> generateRandomEmailCode(@RequestParam(value = "email", required = true) String emailAddress) {
		// !!!!! 警示  post http请求中，如果后端使用requestParam 必须在url中拼接参数 
		// 发送验证码邮件 
		log.info("regist email generate random verifycation code. params : {}", emailAddress);
		String verifyCode = userService.verifyCodeProcess(emailAddress);
		log.info("generated verifyCode : {}", verifyCode); // 可以返回验证码 或 错误信息
		
		return ResponseUtils.success();
	}
	
	@PostMapping(value = "doLogin")
	public ModelAndView doLogin(ModelAndView modelAndView, 
			@RequestParam(value = "username", required = true) String username, 
			@RequestParam(value = "password", required = true) String password) {
		
		// 正确则构造user对象 
		log.info("doLogin --> [ nusername : {}, password : {} ]", username, password);
		
		// 使用用户名称 可以是用户名/注册邮箱 
		User verifiedUser = userService.userLoginCheck(username, password);
		log.info("登陆查询结果 : {}", verifiedUser);
		
		// 没有用户信息, 表示用户名/密码错误  或  用户没有注册 
		if(verifiedUser == null) {
			log.warn("没有查询到用户, 返回登陆界面");
			
			modelAndView.addObject("message", "用户登陆信息不正确或没有注册");
			modelAndView.setViewName("redirect:/page/v1/login");  // modelAndView setObject 是设置请求参数的 
		} else {
			log.info("查询到用户 : {}, 进入用户主页", verifiedUser);
			// 可以不使用这种跳转  直接转向用户主页 内部跳转 传入用户信息即可, 只有错误情况才需要其余保存信息
			// linux 的哲学美学  没有报错就是正确 
			// 重定向后 addObject 会作为钱柜去参数传入 
			
			String redirectUrl = "redirect:/page/v1/users/" + verifiedUser.getId();
			modelAndView.setViewName(redirectUrl);  // 默认foreard 重定向
		}
		
		// 登陆成功进入用户首页
		return modelAndView;
	}
	
	@GetMapping(value = "doLogout")
	public ModelAndView doLogout(ModelAndView modelAndView, @RequestParam(value = "userId", required = true) Long userId) {
		// 用户登出操作和处理 
		// 删除cache中cache缓存对象 
		String message = userService.userLogout(userId);
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("redirect:/page/v1/login");
		return modelAndView;
	}
	
	@GetMapping(value = "regist")
 	public ModelAndView regist(ModelAndView modelAndView, @RequestParam(value = "message", required = false) String message) {
		modelAndView.addObject("message", message);
		modelAndView.setViewName("regist");
		
		return modelAndView;
	}
	
	@PostMapping(value = "doRegist")
	public ModelAndView doRegist(ModelAndView modelAndView, 
			@RequestParam(value = "username", required = true) String userName, 
			@RequestParam(value = "password", required = true) String password, 
			@RequestParam(value = "registemail", required = true) String registEmail, 
			@RequestParam(value = "verifycation_code", required = true) String verifycationCode) {
		
		log.info("doRegidt, username : {}, password : {}, registEmail : {}, verifycationcode : {}", 
				userName, password, registEmail, verifycationCode);
		
		// 检验验证码 
		User registedUser = userService.registCheckProcess(userName, password, registEmail, verifycationCode);
		// 注册成功 返回登陆界面  注册失败, 返回注册界面 
		if(registedUser == null) {
			
			modelAndView.addObject("message", "注册失败请检查");
			modelAndView.setViewName("redirect:page/v1/regist");
		} else {
			
			modelAndView.addObject("message", "注册成功请登录");
			modelAndView.setViewName("redirect:/page/v1/login");
		}
		
		return modelAndView;  // 后面跟url  其余返回templates 模板html
	}
	
}










