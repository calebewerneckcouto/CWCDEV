package com.devsuperior.cwcdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.cwcdev.Exceptions.ForbiddenException;
import com.devsuperior.cwcdev.entities.User;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public void validateSelfOrAdmin(Long userId) {
		User me = userService.authenticated();
	
		
		if(me.hasRole("ROLE_ADMIN")) {
			return;
		}
		
		if(!me.getId().equals(userId)) {
			throw new ForbiddenException("Acess denied.Should be self or admin");
		}
	}
}
