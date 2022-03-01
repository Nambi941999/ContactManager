package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Users;

public interface UserDao extends JpaRepository<Users, Integer>{
	
	@Query("select u from Users u where u.email = :email")
	public Users getUsersByUsername(@Param("email") String email);


}
