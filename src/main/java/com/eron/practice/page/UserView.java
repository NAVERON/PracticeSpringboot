package com.eron.practice.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "page/v1")
public class UserView {
	
	private static final Logger log = LoggerFactory.getLogger(UserView.class);

	@GetMapping(value = "userHome")
	public String userHome() {
		
		log.info("into user home view !");
		
		return "userHome";
	}
}
