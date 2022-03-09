package com.eron.practice.service;

import java.util.List;

import com.eron.practice.model.User;

public interface UserService {

	// 列出所有用户 
	public List<User> all();  
	// 根据 id 获取某位用户信息 
	public User oneByID(Long id); 
	// 注册新用户 
	public User newUser(User user);
	// 删除用户 
	public void deleteUser(Long id);
	// 修改 id 下的用户属性 
	public User modifyUser(Long id, User user);
	// 根据用户名称和密码登陆 
	public User userLoginCheck(String userName, String password);
	// 验证码前后验证
	public String verifyCodeProcess(String emailAddress);
	// 注册验证处理
	public User checkOfRegistProcess(String userName, String password, String registEmail, String verifycationCode);
	
	
}









