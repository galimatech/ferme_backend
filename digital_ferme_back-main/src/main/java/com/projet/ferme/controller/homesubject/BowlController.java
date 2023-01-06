package com.projet.ferme.controller.homesubject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.homesubject.Bowl;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.homesubject.BowlService;

@RestController
public class BowlController {

	@Autowired
	private BowlService service;
	
	@RequestMapping(value = "/api/v1/bowl", method = RequestMethod.POST)
	public Map<String, Object> post(@RequestBody Bowl B) throws NoElementAddException{
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.add(B);
			return  returnMap;
				
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/bowl", method = RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody Bowl B) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.put(B);
			return  returnMap;
		
	}
	
	@RequestMapping(value = "/api/v1/bowl", method = RequestMethod.GET)
	public Map<String, Object> get(){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap = service.findAll();
		return returnMap;
		
	}
	
	@RequestMapping(value = "/api/v1/bowl/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> post(@PathVariable("id") Long id) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			returnMap = service.delete(id);
			return  returnMap;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
}
