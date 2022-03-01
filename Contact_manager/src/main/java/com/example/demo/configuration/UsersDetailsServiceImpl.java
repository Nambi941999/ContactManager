package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.dao.UserDao;
import com.example.demo.entities.Users;

public class UsersDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDao userdao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// fetching user from database
		
		Users users = userdao.getUsersByUsername(username);
		if(users == null) {
			throw new UsernameNotFoundException("Could not found username");
		}
		
		CustomUsersDetails customUsersDetails = new CustomUsersDetails(users);
		return customUsersDetails;
	}

}
