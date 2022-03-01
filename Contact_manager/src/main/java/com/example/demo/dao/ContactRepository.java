package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
	
	//pagination.....
	
	//pageable has two function CURRENT PAGE-PAGE,CONTACT PER PAGE - 5
	@Query("from Contact as c where c.users.id =:usersId")
	public Page<Contact> findContactsByUser(@Param("usersId")int usersId, Pageable pageable);

}
