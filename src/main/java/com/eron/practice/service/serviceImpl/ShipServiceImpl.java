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

	@Override
	public List<Ship> shipsOfUser(Long userId) {
		
		List<Ship> ships = shipDAO.findByUserId(userId);
		return ships;
	}
	
	@Override
	public Ship shipOfUser(Long userId, Long shipId) {
		
		return shipDAO.findShipByUserIdAndShipId(userId, shipId);
	}

	@Override
	public Ship newShipOfUser(Long userId, Ship ship) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteShipOfUser(Long shipId, Long UserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ship modifyShipOfUser(Long userId, Ship ship) {
		// TODO Auto-generated method stub
		return null;
	}

}










