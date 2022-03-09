package com.eron.practice.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.eron.practice.dao.UserDAO;
import com.eron.practice.model.User;
import com.eron.practice.model.constant.CommonConstant;
import com.eron.practice.service.UserService;
import com.eron.practice.utils.MailClientUtils;
import com.eron.practice.utils.RandomGeneratorUtils;



@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource 
	// @Qualifier(value = "") 今后需要更多实现 添加qualifier 
	private UserDAO userDAO;
	@Resource 
	private MailClientUtils mailClient;
	@Resource 
	private StringRedisTemplate stringRedisTemplate;
	
	public List<User> all() { 
		return userDAO.findAll();
	}
	
	public User oneByID(Long id) { 
		log.warn("service implement one method run : {}", id);
		User user = userDAO.findById(id).orElse(null);
		return user;
	}
	
	public User newUser(User user) {
		// 注册新用户  检查邮箱是否重复

		User savedUser = userDAO.save(user);
		return savedUser;
	}

	@Override
	public void deleteUser(Long id) {
		log.warn("UserServiceImpl deleteUser， delete User ID : {}", id);
		userDAO.deleteById(id);
		// 删除动作如何作二次校验 
	}

	@Override
	public User modifyUser( Long id, User user) { // 如果存在id 覆盖属性, 不存在则生成新对象 
		User modifiedUser = null;
		
		Optional<User> userOpt = userDAO.findById(id);
		
		if(userOpt.isPresent()) {
			User updateUser = userOpt.get();
			// 属性完全复制 和 覆盖 
			user.overrideAttributes(updateUser);
			user.setId(id);
			
			modifiedUser = userDAO.save(user);
		} else {
			user.setId(id);
			modifiedUser = userDAO.save(user);
		}
		return modifiedUser;
	}
	
	@Override
	public User userLoginCheck(String userName, String password) {
		return userDAO.getUserByPasswordAndNameOrEmail(userName, password);
	}

	@Override
	public String verifyCodeProcess(String emailAddress) { 
		/**
		 * 0 验证邮箱地址是否合法 
		 * 1  生成随机码 
		 * 2  保存到Redis, 设置过期时间, 比如 5 min 
		 * 3  Redis 正常后, 发送邮件, 不正常返回错误信息展示, 重新注册 
		 */
		String verifyCode = RandomGeneratorUtils.generateString();
		String emailContent = "您好, 您的注册验证码是 : " + verifyCode;
		
		//mailClient.sendSimpleEmail(email, "ERON注册验证码", emailContent);
		log.info("假装邮件已经发送 : {}", emailContent);
		
		// redis 设置
		Boolean redisStatus = stringRedisTemplate.opsForValue().setIfAbsent(CommonConstant.REDIS_VERIFY_PREFIX + emailAddress, verifyCode, 5, TimeUnit.MINUTES);
		
		if(redisStatus) {
			return verifyCode;
		} else {
			return "ERROR";
		}
		
	}

	@Override
	public User checkOfRegistProcess(String userName, String password, String registEmail, String verifycationCode) {
		User registedUser = null;
		String key = CommonConstant.REDIS_VERIFY_PREFIX + registEmail;
		
		// redis 检查
		if(verifycationCode.equals(stringRedisTemplate.opsForValue().get(key))) {
			log.info("验证通过, 注册成功");
			// 验证码通过 
			// 构造用户对象 
			registedUser = User.createBuilder().name(userName).password(password).registEmail(registEmail).build();
			log.info("registed User info : {}", registedUser);
			registedUser = userDAO.save(registedUser);
			
			stringRedisTemplate.delete(key);
		}
		
		return registedUser;

	}
	
	
}





