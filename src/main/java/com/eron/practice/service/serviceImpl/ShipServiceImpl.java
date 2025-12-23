package com.eron.practice.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eron.practice.jpadao.ShipDAO;
import com.eron.practice.model.Ship;
import com.eron.practice.service.ShipService;

@Service
public class ShipServiceImpl implements ShipService { 
	
	private static final Logger log = LoggerFactory.getLogger(ShipServiceImpl.class);
	
	@Resource
	private ShipDAO shipDAO;

	@Override
	public List<Ship> all() {
		// TODO Auto-generated method stub
		return shipDAO.findAll();
	}

	/**
	 * 获取用户的所有船舶数据
	 */
	@Override
	public List<Ship> shipsOfUser(Long userId) {
		
		List<Ship> ships = shipDAO.findByUserId(userId);
		return ships;
	}
	
	/**
	 * 根据用户id和船舶id获取用户特定的船舶信息 
	 */
	@Override
	public Ship shipOfUser(Long userId, Long shipId) {
		
		return shipDAO.findShipByUserIdAndShipId(userId, shipId);
	}

	@Override
	public Ship newShipOfUser(Long userId, Ship ship) {
		ship.setUserId(userId);
		Ship newShip = shipDAO.save(ship);
		
		return newShip;
	}

	@Override
	public Ship deleteShipOfUser(Long userId, Long shipId) {

		Ship deletedShip = shipDAO.deleteShipByUserIdAndShipId(userId, shipId); 
		
		return deletedShip;
	}

	@Override
	public Ship modifyShipOfUser(Long userId, Long shipId, Ship ship) {
		
		// 修改 userId 名下船舶 shipId 的对象, 前提ship对象已经存在在库中
		Ship modifiedShip = null;
		Ship checkShip = shipDAO.findShipByUserIdAndShipId(userId, shipId);
		
		if (checkShip == null) {  // 不存在这个对象, 无法修改, 直接存储
			ship.setUserId(userId);
			ship.setId(shipId);
			
			modifiedShip = shipDAO.save(ship);
		} else {
			
			checkShip.overrideAttributes(ship);
			modifiedShip = shipDAO.save(checkShip);
		}
		
		return modifiedShip;
	}

	@Override
	public Ship newShipOfUser(Long userId, String name, String mmsi, String imoNumber, String callNumber, Integer type,
			Integer electronicType, Float draft) {

		Ship ship = Ship.createBuilder().userId(userId).name(name).mmsi(mmsi)
				.imoNumber(imoNumber).callNumber(callNumber)
				.type(type).electronicType(electronicType)
				.draft(draft)
				.build();
		
		Ship newShip = this.newShipOfUser(userId, ship);
		
		return newShip;
	}
	
}










