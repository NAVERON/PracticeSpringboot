package com.eron.practice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eron.practice.model.ShipTrackPoint;

@Repository 
public interface ShipTrackPointDAO extends JpaRepository<ShipTrackPoint, Long> {

}









