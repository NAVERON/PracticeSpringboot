package com.eron.practice.jpadao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.eron.practice.model.User;


@Repository 
public interface UserDAO extends JpaRepository<User, Long> {
	
//	@Query(value = "insert into User (name, password, registEmail) values (:#{#user.name}, :#{#user.password}, :#{#user.registEmail}) ")
//	public User save(User user);
	
	@Nullable
	@Query(value = "select u from User u where u.id = ?1 ") 
	public Optional<User> findById(Long id);
	
	// 用户登陆 根据用户名/注册邮箱 + 密码, 查询结果可以为null 表示信息错误或没有注册
	@Nullable
	@Query(value = "select u from User u where u.password = ?2 and (u.name = ?1 or u.registEmail = ?1) " ) 
	public User getUserByPasswordAndNameOrEmail(String userName, String password);
}









