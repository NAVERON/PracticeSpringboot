package com.eron.practice.config;


import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eron.practice.service.UserService;

/**
 * 先理解安全架构 再配置
 * @author eron
 *
 */
@Configuration 
@EnableWebSecurity 
// @EnableGlobalAuthentication 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Resource 
	private UserService userService;
	
	private final String[] PATH_RELEASE = {
		"/page/v1/**", 
		"/", 
		"/home", 
		"/about", 
		"/404", 
		"/error"
	};

	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		// 授权配置 
		auth.inMemoryAuthentication()
			.withUser("wangyulong")
			.password(encoder.encode("testing"))
			.roles("ADMIN");
		
	}

	@Override 
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		// 验证配置 
		http.authorizeRequests()
			/**antMatchers (这里的路径)   permitAll 这里是允许所有人 访问*/
			.antMatchers(this.PATH_RELEASE).permitAll()
			/** 映射任何请求 */
			/** 指定任何经过身份验证的用户都允许使用URL。*/
			.anyRequest().authenticated()
			.and().httpBasic()
			/** 指定支持基于表单的身份验证 */
			//.and().formLogin().permitAll()
            /** 允许配置异常处理。可以自己传值进去 使用WebSecurityConfigurerAdapter时，将自动应用此WebSecurityConfigurerAdapter 。*/
            // .and().exceptionHandling()
			.and().csrf().disable()   // cross site request 
			;
		
	}
	
}









