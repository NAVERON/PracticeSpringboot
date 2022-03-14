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

import com.eron.practice.model.CacheStore;
import com.eron.practice.model.Ship;
import com.eron.practice.model.User;
import com.eron.practice.service.ShipService;
import com.eron.practice.service.UserService;
import com.eron.practice.utils.JsonUtils;

@Controller 
@RequestMapping(value = "page/v1") 
public class UserView {
	
	private static final Logger log = LoggerFactory.getLogger(UserView.class);
	
	@Resource 
	private UserService userService;
	
	@Resource 
	private ShipService shipService;

	/**
	 * 用户主页页面url  但是为了确保经过登录验证的,而不是直接输入url的, 增加token验证
	 * @param modelAndView
	 * @param userId  登录用户id
	 * @param loginToken 
	 *  为了确保登录用户是验证过的, 需要生成一个cache token 验证, 或者使用aop 每次查询是否有缓存, 限定只能在登录的时候生成cache 
	 *  token 是缓存的key
	 *  以后使用security框架  现在先不管了, 在缓存中添加cache 验证id是否已经缓存来判断 
	 * @return 用户主页视图
	 */
	@GetMapping(value = "users/{userId}")
	public ModelAndView userHome(ModelAndView modelAndView, @PathVariable(value = "userId") Long userId) {
		
		log.info("into user home view !");
		modelAndView.setViewName("userHome");
		
		// 查询缓存中是否存在  如果登陆过, 直接输入url可以直接进入用户首页 
		User user = userService.oneByIDOfCache(userId);
		//User user = userService.oneByID(userId);
		if(user == null) {  // 没有缓存表示没有登录过
			log.error("userId 没有从缓存中查询到用户, 请先登录");
			
			modelAndView.addObject("message", "缓存无值 请先登录");
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
	
	@PostMapping(value = "users/{userId}/newShip")
	public ModelAndView createShip(ModelAndView modelAndView, 
			@PathVariable(value = "userId") Long pathUserId,
			@RequestParam(value = "userId") Long userId, 
			@RequestParam(value = "name") String name, 
			@RequestParam(value = "mmsi", required = false) String mmsi, 
			@RequestParam(value = "imoNumber", required = false) String imoNumber, 
			@RequestParam(value = "callNumber", required = false) String callNumber, 
			@RequestParam(value = "type", required = false) Integer type, 
			@RequestParam(value = "electronicType", required = false) String electronicType, 
			@RequestParam(value = "draft", required = false) Float draft) {
		// 创建船舶后返回当前用户的主界面  redirect:/page/v1/users  userId = xx
		
		Ship newShip = shipService.newShipOfUser(userId, name, mmsi, imoNumber, callNumber, type, type, draft);
		
		if( !pathUserId.equals(userId) || newShip == null) {  // 冗余校验一下参数 检验创建对象是否成功 
			modelAndView.addObject("message", "创建船舶对象失败, 请重新登录重试");
			modelAndView.setViewName("redirect:/page/v1/login");
		}
		
		String redirectUrl = "redirect:/page/v1/users/" + pathUserId;
		modelAndView.setViewName(redirectUrl);
		return modelAndView;
	}
	
	
}










