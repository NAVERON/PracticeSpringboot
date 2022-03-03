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

import com.eron.practice.model.Ship;
import com.eron.practice.service.ShipService;

@RestController // = Controller ResponseBody (json化)
@RequestMapping(value = "api/v1")
public class ShipManagerApi {
	
	private static final Logger log = LoggerFactory.getLogger(ShipManagerApi.class);
	
	@Resource 
	// @Qualifier(value = "") 
	private ShipService shipService;

	@GetMapping(value = "ships")
	public List<Ship> all(){
		return shipService.all();
	}
	
	@GetMapping(value = "ships/{userId}")
	public List<Ship> shipsOfUserID(@PathVariable(value = "userId") Long userId) {
		return shipService.shipsOfUserID(userId);
	}
	
	@GetMapping(value = "ships/{userId}/{shipId}")
	public Ship shipOfUserID(@PathVariable(value = "userId") Long userId, @PathVariable(value = "shipId") Long shipId) {
		return shipService.shipOfUserID(userId, shipId);
	}
	
	@PostMapping(value = "ships/{userId}")
	public Ship newShipOfUserID(@PathVariable(value = "userId") Long userId, @RequestBody Ship ship) {
		return shipService.newShipOfUserID(userId, ship);
	}
	
	@DeleteMapping(value = "ships/{userId}/{shipId}")
	public void deleteShipOfUserID(@PathVariable(value = "userId") Long userId, @PathVariable(value = "shipId") Long shipId) {
		shipService.deleteShipOfUserID(userId, shipId);
	}
	
	@PutMapping(value = "ships/{userId}")
	public Ship modifyShipOfUserID(@PathVariable(value = "userId") Long userId, @RequestBody Ship ship) {
		return shipService.modifyShipOfUserID(userId, ship);
	}
	
}









