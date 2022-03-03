package com.eron.practice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.eron.practice.model.User;


@Repository 
public interface UserDAO extends JpaRepository<User, Long> {

	@Nullable
	public  User getById(Long id);
}









