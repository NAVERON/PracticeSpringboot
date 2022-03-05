package com.eron.practice.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping(value = "page/v1") 
public class LoginView {
	
	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	@GetMapping(value = "login")
	public String login() {
		return "login";
	}
	
}










