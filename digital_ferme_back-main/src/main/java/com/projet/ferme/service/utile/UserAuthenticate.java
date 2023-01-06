package com.projet.ferme.service.utile;

import com.projet.ferme.entity.person.User;
import com.projet.ferme.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticate {
	
	@Autowired
	private UserRepository repository;
	
	public User getUserAuthenticate() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = repository.findByUsername(username).get();
		
		return user;
		
	}
}
