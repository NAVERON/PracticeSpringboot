package com.eron.practice.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.eron.practice.dao.UserDAO;
import com.eron.practice.model.User;
import com.eron.practice.service.UserService;



@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	// @Qualifier(value = "") 今后需要更多实现 添加qualifier 
	private UserDAO userDAO;
	
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
	
}





