package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ContactRepository;
import com.example.demo.dao.UserDao;
import com.example.demo.entities.Contact;
import com.example.demo.entities.Users;

import net.bytebuddy.asm.Advice.Return;

@RestController
public class SearchController {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserDao userRepository;
	
	//Search handler
//	@GetMapping("/search/{query}")
//	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal)
//	{
//		System.out.println(query);
//
//		Users usersByUsername = userRepository.getUsersByUsername(principal.getName());
//		List<Contact> contacts = this.contactRepository.findByNameContainingAndUsers(query, usersByUsername);
//		
//		return ResponseEntity.ok(contacts);
//	
//	}
	
	

}
