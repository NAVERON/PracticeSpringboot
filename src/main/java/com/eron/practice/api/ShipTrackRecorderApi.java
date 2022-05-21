package com.eron.practice.api;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eron.practice.model.BusinessResponseEntity;
import com.eron.practice.model.ShipTrackPoint;
import com.eron.practice.service.ShipTrackService;
import com.eron.practice.utils.ResponseUtils;



@RestController
@RequestMapping(value = "api/v1")
public class ShipTrackRecorderApi {
	
	private static final Logger log = LoggerFactory.getLogger(ShipTrackRecorderApi.class);
	
	@Resource 
	// @Qualifier(value = "")
	private ShipTrackService shipTrackService;

	@GetMapping(value = "shiptracks/{shipId}")
	public BusinessResponseEntity<Object> trackPointsByShipID(@PathVariable(value = "shipId") Long shipId){
		List<ShipTrackPoint> tracksOfShipTrackPoints = shipTrackService.trackPointsOfShip(shipId);
		
		return ResponseUtils.success(tracksOfShipTrackPoints);
	}
	
	@PostMapping(value = "shiptracks/{shipId}")
	public BusinessResponseEntity<Object> addTrackToShip(@PathVariable(value = "shipId") Long shipId, @RequestBody ShipTrackPoint shipTrackPoint) {
		log.info("传播轨迹点接受到的 => {}", shipTrackPoint.toString());
		
	    ShipTrackPoint newTrackPoint = shipTrackService.addTrackPointsToShip(shipId, shipTrackPoint);
		
		return ResponseUtils.success(newTrackPoint);
	}
	
	@DeleteMapping(value = "shiptracks/{shipId}")
	public BusinessResponseEntity<Object> deleteAllTracksOfShip(@PathVariable(value = "shipid") Long shipId) {
		shipTrackService.deleteAllTracksOfShip(shipId);
		
		return ResponseUtils.success();
	}
	
}









