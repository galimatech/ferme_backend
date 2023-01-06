package com.projet.ferme.controller.homesubject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.homesubject.Enclosure;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.homesubject.EnclosureService;

@RestController
public class EnclosureController {

	
	@Autowired
	private EnclosureService service;
	
	@RequestMapping(value = "/api/v1/enclosure", method=RequestMethod.POST)
	public Map<String, Object> add(@RequestBody Enclosure E) {
			Map<String, Object> returnValues = new HashMap<String, Object>();
			returnValues = service.add(E);
			
			return  returnValues;

	}
	
	@RequestMapping(value= "/api/v1/enclosure", method = RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody Enclosure E) throws NoElementFoundException{
		try {
			Map<String, Object> returnValues = new HashMap<String, Object>();
			returnValues = service.put(E);
			
			return returnValues;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}


	}
	
	@RequestMapping(value= "/api/v1/enclosure", method = RequestMethod.GET)
	public Map<String, Object> get(){

		Map<String, Object> returnValues = new HashMap<String, Object>();
		returnValues = service.findAll();
		
		return returnValues;
	}
	
	@RequestMapping(value= "/api/v1/enclosure/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			Map<String, Object> returnValues = new HashMap<String, Object>();
			returnValues = service.delete(id);
			
			return   returnValues;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}


	}
	
	
}
