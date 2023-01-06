package com.projet.ferme.controller.category;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.category.FishCategory;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.category.CategoryFishService;

@RestController
public class CategoryFishController {

	@Autowired
	private CategoryFishService service;
	
	@RequestMapping(value = "/api/v1/fishCategory", method = RequestMethod.POST)
	public Map<String, Object> post(@RequestBody FishCategory C){
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.add(C);
			return  returnMap;


	}
	
	@RequestMapping(value = "/api/v1/fishCategory", method = RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody FishCategory C) throws NoElementFoundException {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = service.add(C);
			return returnMap;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}

	}
	
	@RequestMapping(value = "/api/v1/fishCategory", method = RequestMethod.GET)
	public Map<String, Object> get() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap = service.findAll();
		return returnMap;
	}
	
	@RequestMapping(value = "/api/v1/fishCategory/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> post(@PathVariable("id") Long id) throws NoElementFoundException {
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
