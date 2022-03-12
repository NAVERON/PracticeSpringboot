package com.eron.practice.service;

import java.util.List;

import com.eron.practice.model.User;

public interface UserService {

	/**
	 *  列出所有用户 
	 * @return 所有用户List
	 */
	public List<User> all();  
	/**
	 *  根据 id 获取某位用户信息 
	 * @param id 用户id
	 * @return user
	 */
	public User oneByID(Long id); 
	/**
	 *  注册新用户 
	 * @param user 构造的新用户信息, 不需要id  time等初始化对象属性
	 * @return 成功创建的对象 
	 */
	public User newUser(User user);
	/**
	 * 根据用户id 删除用户 
	 * @param id 用户id
	 */
	public void deleteUser(Long id);
	/**
	 *  修改 id 下的用户属性 
	 * @param id 被修改的用户id 
	 * @param user 需要修成为的用户属性, 属性覆盖
	 * @return 修改后的用户 
	 */
	public User modifyUser(Long id, User user);
	/**
	 *  根据用户名称和密码登陆 
	 * @param userName
	 * @param password
	 * @return 登录成功的用户
	 */
	public User userLoginCheck(String userName, String password);
	/**
	 *  验证码前后验证 发送邮箱验证码 验证邮箱真实性
	 * @param emailAddress
	 * @return 生成的随机码或者错误message
	 */
	public String verifyCodeProcess(String emailAddress);
	/**
	 *  注册验证处理 注册基本信息和验证码一起做 注册前的检查验证 
	 * @param userName
	 * @param password
	 * @param registEmail
	 * @param verifycationCode
	 * @return 注册成功的用户
	 */
	public User registCheckProcess(String userName, String password, String registEmail, String verifycationCode);
	
	/**
	 * 从缓存中获取用户对象  根据userId 
	 * @param userId
	 * @return 缓存的用户对象 
	 */
	public User oneByIDOfCache(Long userId);
	/**
	 * 用户登出 根据用户id删除缓存cache 
	 * @param userId 用户id
	 * @return 返回登录结果 作为跳转页面的标识 
	 */
	public String userLogout(Long userId);
	
}









