package com.eron.practice.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eron.practice.model.ShipTrackPoint;


@Repository 
public interface ShipTrackPointDAO extends JpaRepository<ShipTrackPoint, Long> {

	@Query(value = "select t from ShipTrackPoint t where t.shipId = ?1 ")
	public List<ShipTrackPoint> findTrackPointsOfShip(Long shipId);
	
	// 自定义删除可以返回删除值
	@Transactional 
	@Query(value = "delete from ShipTrackPoint t where t.shipId = ?1 ")
	public List<ShipTrackPoint> deleteAllTracksOfShip(Long shipId);
	
}









