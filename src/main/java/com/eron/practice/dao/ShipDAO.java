package com.eron.practice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eron.practice.model.Ship;

@Repository 
public interface ShipDAO extends JpaRepository<Ship, Long> {

	@Query(value = "select s from Ship s where s.userId = ?1 ")
	public List<Ship> findByUserId(Long userId);
	
	// nativeQuery = true 可以直接写sql查询
	@Query(value = "select s from Ship s where s.id = ?2 and s.userId = ?1 ")
	public Ship findShipByUserIdAndShipId(Long userId, Long shipId);
	
}









