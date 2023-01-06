package com.projet.ferme.controller.subject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.subject.EggService;
import com.projet.ferme.entity.subject.Egg;
import java.util.Map;


@RestController
public class EggController {

	@Autowired
	private EggService service;
	
	@RequestMapping(value = "/api/v1/egg", method = RequestMethod.POST)
	public Map<String, Object> post(@RequestBody Egg egg) {
			return  service.add(egg);
		
	}
	
	@RequestMapping(value = "/api/v1/egg", method = RequestMethod.GET)
	public Map<String, Object> get() {
		return service.findAll();
	}
	
	@RequestMapping(value = "/api/v1/egg", method = RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody Egg egg) {
			return  service.update(egg);	
		
	}
	
	@RequestMapping(value = "/api/v1/egg/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable("id") Long id) {
			return  service.delete(id);
		
	}
	
	@RequestMapping(value = "/api/v1/egg/{id}", method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable("id") Long id)  throws NoElementFoundException{

		try {
			return service.findByPoultry(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
}
