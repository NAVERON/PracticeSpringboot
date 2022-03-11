package com.eron.practice.page;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eron.practice.model.Ship;
import com.eron.practice.model.User;
import com.eron.practice.service.ShipService;
import com.eron.practice.service.UserService;

@Controller
@RequestMapping(value = "page/v1")
public class UserView {
	
	private static final Logger log = LoggerFactory.getLogger(UserView.class);
	
	@Resource 
	private UserService userService;
	
	@Resource
	private ShipService shipService;

	@GetMapping(value = "users")
	public ModelAndView userHome(ModelAndView modelAndView, @RequestParam(value = "userId") Long userId) {
		
		log.info("into user home view !");
		modelAndView.setViewName("userHome");
		
		User user = userService.oneByID(userId);
		if(user == null) {
			log.error("userId 没有查询到用户");
			
			modelAndView.setViewName("redirect:/page/v1/login");
			return modelAndView;
		}
		
		// 查询用户名下的船舶 
		List<Ship> ships = shipService.shipsOfUser(user.getId());
		
		modelAndView.addObject("user", user);
		modelAndView.addObject("ships", ships);
		modelAndView.setViewName("userHome");
		
		return modelAndView;
	}
	
	
	
}










