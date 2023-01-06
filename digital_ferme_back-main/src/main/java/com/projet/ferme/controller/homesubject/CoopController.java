package com.projet.ferme.controller.homesubject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.homesubject.ChickenCoop;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.homesubject.ChickenCoopService;

@RestController
public class CoopController {


	@Autowired
	private ChickenCoopService coopService;

	@RequestMapping(value="/api/v1/chickenCoop", method = RequestMethod.POST)
	public Map<String, Object> addCoops(@RequestBody ChickenCoop coop) {

			Map<String,Object> returnValues = new HashMap<String, Object>();
			returnValues = coopService.add(coop);
			return returnValues;	
	}
	
	@RequestMapping(value="/api/v1/chickenCoop", method = RequestMethod.PUT)
	public Map<String, Object> updateCoops(@RequestBody ChickenCoop coop) {
		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = coopService.put(coop);
		
		return  returnValues;
	}
	
	@RequestMapping(value="/api/v1/chickenCoop", method = RequestMethod.GET)
	public Map<String, Object> getCoops(){
		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = coopService.findAll();
		
		return returnValues;
	}
	
	@RequestMapping(value="/api/v1/chickenCoop/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteCoops(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			Map<String,Object> returnValues = new HashMap<String, Object>();
			returnValues = coopService.delete(id);
			
			return returnValues;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}


}
