package com.eron.practice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eron.practice.model.Ship;

@Repository 
public interface ShipDAO extends JpaRepository<Ship, Long> {

}









