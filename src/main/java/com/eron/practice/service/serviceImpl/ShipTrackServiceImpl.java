package com.eron.practice.service.serviceImpl;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eron.practice.dao.ShipTrackPointDAO;
import com.eron.practice.model.ShipTrackPoint;
import com.eron.practice.service.ShipTrackService;



@Service
public class ShipTrackServiceImpl implements ShipTrackService {
	
	private static final Logger log = LoggerFactory.getLogger(ShipTrackServiceImpl.class);
	
	@Resource
	private ShipTrackPointDAO shipTrackPointDAO;

	@Override
	public ShipTrackPoint addTrackPointsToShip(Long shipId, ShipTrackPoint shipTrackPoint) {
		return null;
	}

	@Override
	public List<ShipTrackPoint> trackPointsOfShip(Long shipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllTracksOfShip(Long shipId) {
		// TODO Auto-generated method stub
		
	}

}









