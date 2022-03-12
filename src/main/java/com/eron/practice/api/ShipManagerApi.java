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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eron.practice.model.ResponseEntity;
import com.eron.practice.model.Ship;
import com.eron.practice.service.ShipService;
import com.eron.practice.utils.ResponseUtils;



@RestController // = Controller ResponseBody (json化)
@RequestMapping(value = "api/v1")
public class ShipManagerApi {
	
	private static final Logger log = LoggerFactory.getLogger(ShipManagerApi.class);
	
	@Resource 
	// @Qualifier(value = "") 
	private ShipService shipService;

	@GetMapping(value = "ships")
	public ResponseEntity<Object> all(){
		List<Ship> all = shipService.all();
		
		return ResponseUtils.success(all);
	}
	
	@GetMapping(value = "ships/{userId}")
	public ResponseEntity<Object> shipsOfUser(@PathVariable(value = "userId") Long userId) {
		List<Ship> shipsOfUser = shipService.shipsOfUser(userId);
		
		return ResponseUtils.success(shipsOfUser);
	}
	
	@GetMapping(value = "ships/{userId}/{shipId}")
	public ResponseEntity<Object> shipOfUser(@PathVariable(value = "userId") Long userId, @PathVariable(value = "shipId") Long shipId) {
		Ship shipOfUserShip = shipService.shipOfUser(userId, shipId);
		
		return ResponseUtils.success(shipOfUserShip);
	}
	
	@PostMapping(value = "ships/{userId}")
	public ResponseEntity<Object> newShipOfUser(@PathVariable(value = "userId") Long userId, @RequestBody Ship ship) {
		Ship newShip =  shipService.newShipOfUser(userId, ship);
		
		return ResponseUtils.success(newShip);
	}
	
	@DeleteMapping(value = "ships/{userId}/{shipId}")
	public ResponseEntity<Object> deleteShipOfUser(@PathVariable(value = "userId") Long userId, @PathVariable(value = "shipId") Long shipId) {
		shipService.deleteShipOfUser(userId, shipId);
		
		return ResponseUtils.success();
	}
	
	@PutMapping(value = "ships/{userId}/{shipId}")
	public ResponseEntity<Object> modifyShipOfUser(
			@PathVariable(value = "userId") Long userId, 
			@PathVariable(value = "shipId") Long shipId, 
			@RequestBody Ship ship) {
		Ship updatedShip =  shipService.modifyShipOfUser(userId, shipId, ship);
		
		return ResponseUtils.success(updatedShip);
	}
	
}









