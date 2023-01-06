package com.projet.ferme.service.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.person.Role;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.repository.UserRepository;
import com.projet.ferme.repository.person.RoleRepository;

@Service
public class UserService {

	@Autowired
	private  UserRepository repository;
	@Autowired
	private RoleRepository roleRepository;
	
	public Map<String, Object> add(User user) {
		
		Map<String, Object> map = new HashMap<>();
		
		if (!controle(user).equals("OK")) {
			map.put("success", false);
			map.put("message", controle(user));
		}else {
			user.setUsername(generateUsername(user.getLastname()));
			user.setPassword(generatePassWord(user.getLastname()));
			
			User newUser = repository.save(user);
			if (newUser == null) {
				map.put("success", false);
				map.put("message", "Echec de l'enregistrement");
			}else {
				map.put("success", true);
				map.put("message", "Enregistrement réussi");
				map.put("user", newUser);
			}
		}
		return map;
	}
	
	public Map<String, Object> findAll() {
		
		Map<String, Object> map = new HashMap<>();
		List<User> users = repository.findAll();
		users = users.stream().filter(u -> !u.getRole().getRoleName().equals("admin")).collect(Collectors.toList());
		map.put("success", true);
		map.put("users", users);
		
		return map;
	}
	
	public Map<String, Object> update(User newUser) {
		
		Map<String, Object> map = new HashMap<>();
		Optional<User> oldUser = repository.findById(newUser.getId());
		if(oldUser.isEmpty()) {
			map.put("success", false);
			map.put("message", "l'utilisateur ne se trouve pas dans la base");
		}else if(!controle(newUser).equals("OK")) {
				map.put("success", false);
				map.put("message",controle(newUser));
		}else {
			newUser.setPassword(generatePassWord(newUser.getPassword()));
			newUser.setUsername(generateUsername(newUser.getLastname()));
			User savedUser = repository.save(newUser);
			if (savedUser == null) {
				map.put("success", false);
				map.put("message", "Echec de l'enregistrement");
			}else {
				map.put("success", true);
				map.put("message", "Enregistrement réussi");
				map.put("user", savedUser);
			}

		}
		return map;
	}
	
	public Map<String, Object> active(Long id) {
		Map<String, Object> map = new HashMap<>();
		Optional<User> oldUser = repository.findById(id);
		if(oldUser.isEmpty()) {
			map.put("success", false);
			map.put("message", "l'utilisateur ne se trouve pas dans la base");
		}else {
			oldUser.get().setActive(!oldUser.get().isActive());
			User savedUser = repository.save(oldUser.get());
			if (savedUser == null) {
				map.put("success", false);
				map.put("message", "Echec de l'enregistrement");
			}else {
				map.put("success", true);
				map.put("message", "Enregistrement réussi");
				map.put("user", savedUser);
			}
		}
		return map;
	}
	
	public Map<String, Object> postRole(Role role){
		
		Map<String, Object> map = new HashMap<String, Object>();
		Role savedRole = roleRepository.save(role);
		if(role == null) {
			map.put("success", false);
			map.put("message", "Echec de l'enregistrement");
		}else {
			map.put("success", true);
			map.put("role", savedRole);
			map.put("message", "Enregistré avec succès");
		}
		
		return map;
	}
	
	public Map<String, Object> findAllRole(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Role> roles = roleRepository.findAll();
		roles = roles.stream().filter(r -> !r.getRoleName().equals("admin")).collect(Collectors.toList());
		map.put("success", true);
		map.put("roles", roles);
		
		return map;
	}

 	private  String generateUsername(String lastname) {
		
		Random random = new Random();
		List<User> users = repository.findAll();
		String userName= "";
		while (true) {
		
			int index = random.nextInt(10);
			final String userString = lastname.concat("_"+index);
			boolean exist = users.stream().noneMatch(user -> user.getUsername().equals(userString));
			userName = userString;
			if(exist)
				break;
		}
			return userName;
	}
	
	private static String controle(User user) {
		
		if(user.getFirstname().isEmpty())
			return "Le prénom est obligatoire";
		else if (user.getLastname().isEmpty())
			return "Le nom est obligatoire";
		else 
			return "OK";
	}
	
	private static String generatePassWord(String lastName) {
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		String encodedPassword = passwordEncoder.encode(lastName);
		
		return encodedPassword;
	}

}
