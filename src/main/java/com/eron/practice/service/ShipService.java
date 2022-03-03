package com.eron.practice.service;

import java.util.List;

import com.eron.practice.model.Ship;


/**
 * 船舶信息注册服务
 * 
 * @author eron
 *
 */
public interface ShipService {
	
	// 获取所有船舶  不区分用户id 
	public List<Ship> all();  
	// 获取某个用户名下的所有船舶 
	public List<Ship> shipsOfUserID(Long userId); 
	// 用户名下某一个船舶信息 
	public Ship shipOfUserID(Long userId, Long shipId);
	// 创建用户名下的船舶 
	public Ship newShipOfUserID(Long userId, Ship ship);
	// 删除用户名下的 某一艘船舶 
	public void deleteShipOfUserID(Long UserId, Long shipId);
	// 修改用户名下的船舶 
	public Ship modifyShipOfUserID(Long userId, Ship ship);
	
}








