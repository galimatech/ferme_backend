package com.projet.ferme.controller.person;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.projet.ferme.entity.person.Role;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.service.person.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/api/v1/user", method = RequestMethod.POST)
	public Map<String, Object> addUser(@RequestBody User user) {
			return service.add(user);
	
	}
	
	@RequestMapping(value = "/api/v1/user", method = RequestMethod.PUT)
	public Map<String, Object> updateUser(@RequestBody User user) {
			return service.update(user);

	}
	
	@RequestMapping(value = "/api/v1/user/{id}", method = RequestMethod.GET)
	public Map<String, Object> activeUser(@PathVariable("id") Long id) {
			return service.active(id);
	
	}
	
	@RequestMapping(value = "/api/v1/user", method = RequestMethod.GET)
	public Map<String, Object> getUsers() {
		return service.findAll();
	}
	
	@RequestMapping(value = "/api/v1/role", method = RequestMethod.POST)
	public Map<String, Object> addRole(@RequestBody Role role){
			return service.postRole(role);
	
	}
	
	@RequestMapping(value = "/api/v1/role", method = RequestMethod.GET)
	public Map<String, Object> getRole(){
		return service.findAllRole();
	}
}
