package com.projet.ferme.controller.category;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.category.Product;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.category.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@RequestMapping(value = "/api/v1/product", method = RequestMethod.POST)
	public Map<String, Object> post(@RequestBody Product prod) {
			return service.add(prod);

	
	}
	
	@RequestMapping(value = "/api/v1/product", method = RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody Product prod) {

			return service.update(prod);
	}
	
	@RequestMapping(value = "/api/v1/product", method = RequestMethod.GET)
	public Map<String, Object> get(){
		return service.findAll();
	}
	
	@RequestMapping(value = "/api/v1/product/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return service.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	
	}
}
