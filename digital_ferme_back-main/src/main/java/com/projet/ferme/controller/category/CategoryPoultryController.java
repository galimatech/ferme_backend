package com.projet.ferme.controller.category;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.category.PoultryCategory;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.category.CategoryPoultryService;

@RestController
public class CategoryPoultryController {

	@Autowired
	private CategoryPoultryService service;
	
	@RequestMapping(value = "/api/v1/poultryCategory", method = RequestMethod.POST)
	public Map<String, Object> post(@RequestBody PoultryCategory C)  {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.add(C);
			return  returnMap;


	}
	
	@RequestMapping(value = "/api/v1/poultryCategory", method = RequestMethod.PUT)
	public 	Map<String, Object> put(@RequestBody PoultryCategory C) throws NoElementAddException  {
	
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.add(C);
			return returnMap;

	}
	
	@RequestMapping(value = "/api/v1/poultryCategory", method = RequestMethod.GET)
	public Map<String, Object> get() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap = service.findAll();
		return returnMap;
	}
	
	@RequestMapping(value = "/api/v1/poultryCategory/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> post(@PathVariable("id") Long id) throws NoElementFoundException {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.delete(id);
			return  returnMap;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}

	}
}
