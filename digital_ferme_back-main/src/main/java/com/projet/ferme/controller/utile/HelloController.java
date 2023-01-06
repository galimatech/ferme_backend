package com.projet.ferme.controller.utile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping(value = "/api/v1/hello", method = RequestMethod.GET)
	public String index(){
		return "Hello SprintBoot";
	}
}
