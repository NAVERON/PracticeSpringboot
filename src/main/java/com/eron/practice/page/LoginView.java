package com.eron.practice.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping(value = "page/v1") 
public class LoginView {
	
	@GetMapping(value = "404")
	public String notFound() {
		return "404";
	}
	
	@GetMapping(value = "home")
	public String home() {
		return "index";
	}
	
	@GetMapping(value = "about")
	public String about() {
		return "about";
	}
	
	
}










