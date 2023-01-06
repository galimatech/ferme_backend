package com.projet.ferme.controller.homesubject;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.homesubject.Planting;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.homesubject.PlantingService;

@RestController
public class PlantingController {

	@Autowired
	private PlantingService service;
	
	@RequestMapping(value = "/api/v1/planting", method = RequestMethod.POST)
	public Map<String, Object> post(@RequestBody Planting P) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.add(P);
			return  returnMap;


	}
	
	@RequestMapping(value = "/api/v1/planting", method = RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody Planting P) {

			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.put(P);
			return returnMap;	
	}
	
	@RequestMapping(value = "/api/v1/planting", method = RequestMethod.GET)
	public Map<String, Object> get(){
	
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.findAll();
			return returnMap;
			
	}
	
	@RequestMapping(value = "/api/v1/planting/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> post(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.delete(id);
			return returnMap;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}

	}
}
