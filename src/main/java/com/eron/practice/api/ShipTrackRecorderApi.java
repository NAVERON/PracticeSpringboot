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

import com.eron.practice.model.ShipTrackPoint;
import com.eron.practice.service.ShipTrackService;



@RestController
@RequestMapping(value = "api/v1")
public class ShipTrackRecorderApi {
	
	private static final Logger log = LoggerFactory.getLogger(ShipTrackRecorderApi.class);
	
	@Resource 
	// @Qualifier(value = "")
	private ShipTrackService shipTrackService;

	@GetMapping(value = "shiptracks/{shipId}")
	public List<ShipTrackPoint> trackPointsByShipID(@PathVariable(value = "shipId") Long shipId){
		return shipTrackService.trackPointsOfShip(shipId);
	}
	
	@PostMapping(value = "shiptracks/{shipId}")
	public ShipTrackPoint addTrackToShip(@PathVariable(value = "shipId") Long shipId, @RequestBody ShipTrackPoint shipTrackPoint) {
		return shipTrackService.addTrackPointsToShip(shipId, shipTrackPoint);
	}
	
	@DeleteMapping(value = "shiptracks/{shipId}")
	public void deleteAllTracksOfShip(@PathVariable(value = "shipid") Long shipId) {
		shipTrackService.deleteAllTracksOfShip(shipId);
	}
	
}









