package com.eron.practice.api;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eron.practice.model.User;
import com.eron.practice.service.UserService;

@RestController
@RequestMapping(value = "api/v1")
public class UserManagerApi { 
	
	private static final Logger log = LoggerFactory.getLogger(UserManagerApi.class);
	
	@Resource
	// @Qualifier // 以后如果有其他的实现, 补充空值为设定值 
	private UserService userService; 

	@GetMapping(value = "users")
	public List<User> all() {  // 获取局所有用户 
		List<User> all = userService.all();
		log.info("all user : {}", all);
		return all;
	}
	
	@GetMapping(value = "users/{id}")
	public User one(@PathVariable(value = "id") Long id) {  // 获取单个登陆用户 
		User one = userService.oneByID(id);
		log.error("print one {}", one);
		
		return one;
	}
	
	@PostMapping(value = "users")
	public User newUser(@RequestBody User user) {  // 注册新用户 
		return userService.newUser(user);
	}
	
	@DeleteMapping(value = "users/{id}")
	public void deleteUser(@PathVariable(value = "id") Long id) {  // 删除单个用户 
		userService.deleteUser(id);
	}
	
	@PutMapping(value = "users/{id}")
	public User modifyUser(@PathVariable(value = "id") Long id, @RequestBody User user) {  // 根据id修改用户属性 
		return userService.modifyUser(id, user);
	}
	
}










