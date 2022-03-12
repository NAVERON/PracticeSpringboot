package com.eron.practice.page;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eron.practice.model.Ship;
import com.eron.practice.model.ShipTrackPoint;
import com.eron.practice.model.User;
import com.eron.practice.service.ShipService;
import com.eron.practice.service.ShipTrackService;
import com.eron.practice.service.UserService;

@Controller
@RequestMapping(value = "page/v1")
public class ShipView {
	
	private static final Logger log = LoggerFactory.getLogger(ShipView.class);
	
	@Resource 
	private UserService userService;
	
	@Resource 
	private ShipService shipService;
	
	@Resource
	private ShipTrackService shipTrackService;

	@GetMapping(value = "ships/{userId}/{shipId}") 
	public ModelAndView shipHome(ModelAndView modelAndView, 
			@PathVariable(value = "userId") Long userId, 
			@PathVariable(value = "shipId") Long shipId) {
		
		log.info("get ship home view ! userId : {}, shipId : {}", userId, shipId);
		User cachedUser = userService.oneByIDOfCache(userId);
		Ship ship = shipService.shipOfUser(userId, shipId);
		
		// 用户没有登陆 需要重新登录 
		if(cachedUser == null || ship == null) {
			log.error("userId 没有从缓存中查询到用户, 请先登录");
			
			modelAndView.addObject("message", "缓存无值 请先登录");
			modelAndView.setViewName("redirect:/page/v1/login");
			return modelAndView;
		}
		List<ShipTrackPoint> shipTracks = shipTrackService.trackPointsOfShip(shipId);
		
		modelAndView.addObject("user", cachedUser);
		modelAndView.addObject("ship", ship);
		modelAndView.addObject("shipTracks", shipTracks); // 展示一艘船舶的所有轨迹点   可以直接从接口获取数据, 这里可以不重复创建接口 
		
		modelAndView.setViewName("shipHome");
		
		return modelAndView;
	}
	
	@PostMapping(value = "ships/{userId}/{shipId}/updateShip")
	public ModelAndView tracksOfShip(ModelAndView modelAndView, 
			@PathVariable(value = "userId") Long userId, 
			@PathVariable(value = "shipId") Long shipId) {
		// 更新用户名下某一艘船舶的基本信息 
		
		User cachedUser = userService.oneByIDOfCache(userId);
		
		Ship ship = shipService.shipOfUser(userId, shipId);
		
		// 用户没有登陆 需要重新登录 
		if(cachedUser == null || ship == null) {
			log.error("userId 没有从缓存中查询到用户, 请先登录");
			
			modelAndView.addObject("message", "缓存无值请先登录");
			modelAndView.setViewName("redirect:/page/v1/login");
			return modelAndView;
		}
		
		return modelAndView;
	}
	
}






