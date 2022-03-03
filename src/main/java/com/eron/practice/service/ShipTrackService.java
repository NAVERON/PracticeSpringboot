package com.eron.practice.service;

import java.util.List;

import com.eron.practice.model.ShipTrackPoint;

public interface ShipTrackService {
	
	// 根据id 插入一条船舶轨迹点 
	public ShipTrackPoint addTrackPointsToShip(Long shipId, ShipTrackPoint shipTrackPoint);
	// 根据船舶id获取路径轨迹点 
	public List<ShipTrackPoint> trackPointsOfShip(Long shipId); 
	// 删除某条船的所有诡计点  根据shipid  // 不允许删除某一个轨迹点 
	public void deleteAllTracksOfShip(Long shipId); 
	
}
