package com.projet.ferme.controller.subject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.outsubject.OutCattle;
import com.projet.ferme.entity.subject.Cattle;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.outsubject.OutCattleService;
import com.projet.ferme.service.subject.CattleService;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CattleController {

	@Autowired
	private CattleService cattleService;
	@Autowired
	private OutCattleService outService;
	
	@RequestMapping(value="/api/v1/cattle", method=RequestMethod.POST)
	public Map<String, Object> addCattle(@RequestBody Cattle cattle) {
			Map<String,Object> returnValues = new HashMap<String, Object>();
			returnValues = cattleService.add(cattle);
			return returnValues;
			
	}
	
	@RequestMapping(value="api/v1/cattle",method=RequestMethod.GET)
	public Map<String, Object> getCattle(){

		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = cattleService.findAll();
		
		return returnValues;
	}
	
	@RequestMapping(value="api/v1/cattle/byName",method=RequestMethod.GET)
	public Map<String, Object> getCattle(@RequestParam String name){

		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = cattleService.findByName(name);
		
		return returnValues;
	}
	
	@RequestMapping(value="api/v1/cattle/byPresent",method=RequestMethod.GET)
	public Map<String, Object> getCattle(@RequestParam Boolean present){

		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = cattleService.findByPresent(present);
		
		return returnValues;
	}
	
	@RequestMapping(value="/api/v1/cattle", method=RequestMethod.PUT)
	public Map<String, Object> putCattle(@RequestBody Cattle cattle) throws NoElementFoundException{
		try {
			Map<String,Object> returnValues = new HashMap<String, Object>();
			returnValues = cattleService.put(cattle);
			
			return returnValues;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
		
	}
	
	
	@RequestMapping(value="/api/v1/cattle/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteCattle(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			Map<String,Object> returnValues = new HashMap<String, Object>();
			returnValues = cattleService.delete(id);
			
			return returnValues;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value="/api/v1/cattle/count", method=RequestMethod.GET)
	public Map<String, Object> countCattle(){
		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = cattleService.count();
		return returnValues;
	}
	
	@RequestMapping(value = "/api/v1/enclosure/cattle/{id}", method = RequestMethod.GET)
	public Map<String, Object> cattleByEnclosure(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return cattleService.cattleByEnclosure(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/category/cattle/{id}", method = RequestMethod.GET)
	public Map<String, Object> cattleByCategory(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return cattleService.cattleByCategory(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/outCattle", method = RequestMethod.POST)
	public Map<String, Object> addOut(@RequestBody OutCattle out) {
			return outService.add(out);
		
	}
	
	@RequestMapping(value = "/api/v1/outCattle", method = RequestMethod.PUT)
	public Map<String, Object> updateOut(@RequestBody OutCattle out) {
			return outService.put(out);	
	}
	
	@RequestMapping(value = "/api/v1/outCattle/{id}", method = RequestMethod.GET)
	public Map<String, Object> getOut(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return outService.findByCattleId(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/outCattle/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteOut(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return outService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
}
