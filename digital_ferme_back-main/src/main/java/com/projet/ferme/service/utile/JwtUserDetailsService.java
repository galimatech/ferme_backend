package com.projet.ferme.service.utile;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.person.MyUserDetails;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	 @Autowired
	    UserRepository userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Optional<User> user = userRepository.findByUsername(username);

	        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

	        return user.map(MyUserDetails::new).get();
	    }

}