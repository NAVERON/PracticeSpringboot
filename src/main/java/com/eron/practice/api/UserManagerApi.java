package com.eron.practice.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eron.practice.model.BusinessResponseEntity;
import com.eron.practice.model.User;
import com.eron.practice.service.UserService;
import com.eron.practice.utils.ResponseUtils;

@RestController
@RequestMapping(value = "api/v1")
public class UserManagerApi { 
	// 单独请求, 不和页面的控制混合   这种的单独用在客户端吧  以后查找混合的方式 
	private static final Logger log = LoggerFactory.getLogger(UserManagerApi.class);
	
	private final ThreadLocal<String> tesThreadLocal = new ThreadLocal<>();  // 多线程场景下公共变量的使用 
	
	@Resource
	// @Qualifier // 以后如果有其他的实现, 补充空值为设定值 
	private UserService userService; 
	
	@GetMapping(value = "test")  // 测试
	public BusinessResponseEntity<Object> test(@ModelAttribute(value = "custome") Map<String, String> customeAttr) {
		log.info("测试引用自定义 modelAttribute : {}", customeAttr);
		// 测试在 ApiControllerAdvice 中 model attribude 自定义属性
		
		return ResponseUtils.success();
	}

	@GetMapping(value = "users")
	public BusinessResponseEntity<Object> all() {  // 获取局所有用户 
		List<User> all = userService.all();
		log.info("all user : {}", all);
		return ResponseUtils.success(all);
	}
	
	@GetMapping(value = "users/{id}")
	public BusinessResponseEntity<Object> one(@PathVariable(value = "id") Long id) {  // 获取单个登陆用户 
		User one = userService.oneByID(id);
		log.error("print one {}", one);
		
		return ResponseUtils.success(one);
	}
	
	@PostMapping(value = "users")
	public BusinessResponseEntity<Object> newUser(@RequestBody User user) {  // 注册新用户 
		User newUser =  userService.newUser(user);
		
		return ResponseUtils.success(newUser);
	}
	
	@DeleteMapping(value = "users/{id}")
	public BusinessResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {  // 删除单个用户 
		userService.deleteUser(id);
		
		return ResponseUtils.success();
	}
	
	@PutMapping(value = "users/{id}")
	public BusinessResponseEntity<Object> modifyUser(@PathVariable(value = "id") Long id, @RequestBody User user) {  // 根据id修改用户属性 
		User updatedUser =  userService.modifyUser(id, user);
		
		return ResponseUtils.success(updatedUser);
	}
	
}










