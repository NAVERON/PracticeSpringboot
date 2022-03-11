package com.eron.practice.page;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eron.practice.service.ShipService;
import com.eron.practice.service.ShipTrackService;

@Controller
@RequestMapping(value = "page/v1")
public class ShipView {
	
	private static final Logger log = LoggerFactory.getLogger(ShipView.class);
	
	@Resource 
	private ShipService shipService;
	
	@Resource
	private ShipTrackService shipTrackService;

	@GetMapping(value = "ships")
	public ModelAndView shipHome(ModelAndView modelAndView, @RequestParam(value = "shipId") Long shipId) {
		
		return modelAndView;
	}
	
	@GetMapping(value = "")
	public ModelAndView tracksOfShip(ModelAndView modelAndView) {
		// 展示一艘船舶的所有轨迹点   可以直接从接口获取数据, 这里可以不重复创建接口 
		
		return modelAndView;
	}
	
}






