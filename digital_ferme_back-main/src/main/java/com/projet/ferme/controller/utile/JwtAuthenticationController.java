package com.projet.ferme.controller.utile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.config.JwtTokenUtil;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.utils.JwtRequest;
import com.projet.ferme.repository.UserRepository;

@RestController
public class JwtAuthenticationController {

	@Autowired
	private UserRepository userRepository;
	 
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public Map<String, Object> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		Optional<User> user = userRepository.findByUsername(authenticationRequest.getUsername());
		
		Map<String,Object> myMapLogger = new HashMap<String, Object>();
		
		Map<String,String> myMap = new HashMap<String, String>();
		myMap.put("token", token);
		myMap.put("firstname",user.get().getFirstname());
		myMap.put("lastname",user.get().getLastname());
		myMap.put("username",user.get().getUsername());
		myMap.put("role",user.get().getRole().getRoleName());
		//return ResponseEntity.ok(new JwtResponse(token));
		
		myMapLogger.put("data", myMap);
		
		return  myMapLogger;
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@RequestMapping(value = "/api/v1/logout", method = RequestMethod.POST)
	public ResponseEntity<?> logout(){
		return ResponseEntity.ok(null);
	}
}
