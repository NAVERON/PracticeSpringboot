package com.eron.practice.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.eron.practice.exception.CustomeGlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eron.practice.jpadao.UserDAO;
import com.eron.practice.model.CacheStore;
import com.eron.practice.model.User;
import com.eron.practice.model.constant.CommonConstant;
import com.eron.practice.service.UserService;
import com.eron.practice.utils.MailClientUtils;
import com.eron.practice.utils.RandomGeneratorUtils;
import com.eron.practice.utils.RedisClusterUtils;


@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource 
	// @Qualifier(value = "") 今后需要更多实现 添加qualifier 
	private UserDAO userDAO;

	@Resource 
	private MailClientUtils mailClient;

	@Resource 
	private RedisClusterUtils redisClusterUtils;

	@Resource
	private CacheStore<User> loginUserCache;  // 缓存登录的用户对象 本地缓存， 实际项目中可以使用全局缓存 redis等
	
	public List<User> all() { 
		return userDAO.findAll();
	}
	
	public User oneByID(Long id) { 
		log.warn("service implement one method run : {}", id);
		try {
			return userDAO.findById(id).orElseThrow(() -> new CustomeGlobalException("no user by id"));
		} catch (CustomeGlobalException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 这里的用户名称设置为注册邮箱名称   ==  业务关系转换   用户名称或者邮箱均可 名称可能会重复 
		// find by username or registmail

		return userDAO.findByUserNameOrRegistEmail(username).orElseGet(User::new);
	}

	public User newUser(User user) {
		// 注册新用户  检查邮箱是否重复

		return userDAO.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		log.warn("UserServiceImpl deleteUser， delete User ID : {}", id);
		userDAO.deleteById(id);
		// 删除动作如何作二次校验 
	}

	@Override
	public User modifyUser(Long id, User user) { // 如果存在id 覆盖属性, 不存在则生成新对象 
		User modifiedUser = null;
		
		Optional<User> userOpt = userDAO.findById(id);
		
		if(userOpt.isPresent()) {
			User updateUser = userOpt.get();
			// 属性完全复制 和 覆盖 
			updateUser.overrideAttributes(user);
			
			modifiedUser = userDAO.save(updateUser);
		} else {
			user.setId(id);  // 相当于新创建一个对象
			modifiedUser = userDAO.save(user);
		}
		return modifiedUser;
	}
	
	@Override
	public User userLoginCheck(String userName, String password) {
		User verifiedUser = null;
		verifiedUser = userDAO.getUserByPasswordAndNameOrEmail(userName, password);
		
		if(verifiedUser == null) {
			return null;
		}
		
		// 如果查询正确, 缓存用户对象
		loginUserCache.set(String.valueOf(verifiedUser.getId()), verifiedUser);
		
		return verifiedUser;
	}

	@Override
	public String verifyCodeProcess(String emailAddress) { 
		/**
		 * 0 验证邮箱地址是否合法    邮箱是数据库唯一索引，不能重复，需要提前判断 
		 * 1  生成随机码  如果短时间内吸纳共同的邮箱注册, 不需要重复生成，或者直接覆盖之前生成的验证码
		 * 2  保存到Redis, 设置过期时间, 比如 5 min 
		 * 3  Redis 正常后, 发送邮件, 不正常返回错误信息展示, 重新注册 
		 */
		String verifyCode = RandomGeneratorUtils.generateString();
		String emailContent = "您好, 您的注册验证码是 : \n" + verifyCode;
		
		//mailClient.sendSimpleEmail(email, "ERON注册验证码", emailContent);
		log.info("假装邮件已经发送 : {}", emailContent);
		
		// redis 设置
		String redisResult = redisClusterUtils.setRedisEmailVerifyCode(emailAddress, verifyCode, 5, TimeUnit.MINUTES);
		
		log.warn("redisResult : {}", redisResult);
		return redisResult;
		
	}

	@Override
	public User registCheckProcess(String userName, String password, String registEmail, String verifycationCode) {
		User registedUser = null;
		
		// redis 检查
		if(redisClusterUtils.checkRedisEmailVerifyCode(registEmail, verifycationCode)) {
			log.info("验证通过, 注册成功");
			// 验证码通过 
			// 构造用户对象 
			registedUser = User.createBuilder().name(userName).password(password).registEmail(registEmail).build();
			registedUser = userDAO.save(registedUser);
			log.info("register User info : {}", registedUser);
		}
		
		return registedUser;

	}

	/**
	 * 缓存应当穿插于具体的方法中, 不用单独提供方发, 逻辑层直接判断取缓存还是直接返回对象 并缓存
	 * @param userId
	 * @return
	 */
	@Override
	public User oneByIDOfCache(Long userId) {
		// 从cache中获取user
		User cachedUser = loginUserCache.get(String.valueOf(userId));
		log.info("cached User : {}", cachedUser);
		
		return cachedUser;
	}

	@Override
	public String userLogout(Long userId) {
		String message = "登出成功";
		Boolean delStatus = loginUserCache.del(String.valueOf(userId));
		if(!delStatus) {
			message = "登出出现错误, 请重试";
		}
		
		return message;
	}
	
	
}










