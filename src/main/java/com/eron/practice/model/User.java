package com.eron.practice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;


@Entity 
@Table(name = "user") 
public class User implements Cloneable {
	
	private static final Logger log = LoggerFactory.getLogger(User.class);
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id; // 自增主键id
	@Column(name = "name")
	private String name; // 用户名称  **必须赋值**
	@Column(name = "password")
	private String password; // 用户设定的密码 md5运算  **必须赋值**
	@Column(name = "regist_email")
	private String registEmail; // 注册邮箱  **必须赋值** 
	@Column(name = "create_time")
	private LocalDateTime createTime; // 创建时间
	@Column(name = "update_time")
	private LocalDateTime updateTime; // 最近一次修改属性的时间 
	
	public User() {
		this.name = "NULL";
		this.password = DigestUtils.md5DigestAsHex("".getBytes());
		this.registEmail = "NULL";
	}
	
	public User(String name, String password, String registEmail) {
		this.name = name;
		this.password = DigestUtils.md5DigestAsHex(password.getBytes());
		this.registEmail = registEmail;
	}
	
	public User(Builder builder) {
		
	}
	
	public static Builder createBuilder() {
		return new Builder();
	}
	
	public static class Builder {
		
		public User build() {
			return new User(this);
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	// id 和 updatetime 不覆盖, 其余属性覆盖 
	public void overrideAttributes(User another) {
		if (another == null) {
			log.error("another User is null !!!");
			return;
		}
		this.name = another.name;
		this.password = another.password;
		this.registEmail = another.registEmail;
		this.createTime = another.createTime;
	}

	/**
	 * pojo 通用 getter 和 setter 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistEmail() {
		return registEmail;
	}

	public void setRegistEmail(String registEmail) {
		this.registEmail = registEmail;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	
	
}









