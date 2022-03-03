package com.eron.practice.service.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eron.practice.model.Ship;
import com.eron.practice.service.ShipService;

@Service
public class ShipServiceImpl implements ShipService { 
	
	private static final Logger log = LoggerFactory.getLogger(ShipServiceImpl.class);

	@Override
	public List<Ship> all() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ship> shipsOfUserID(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Ship shipOfUserID(Long userId, Long shipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ship newShipOfUserID(Long userId, Ship ship) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteShipOfUserID(Long shipId, Long UserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ship modifyShipOfUserID(Long userId, Ship ship) {
		// TODO Auto-generated method stub
		return null;
	}

}










