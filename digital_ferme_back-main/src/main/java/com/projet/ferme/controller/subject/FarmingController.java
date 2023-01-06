package com.projet.ferme.controller.subject;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.outsubject.HarvestSpeculation;
import com.projet.ferme.entity.subject.Speculation;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.outsubject.HarvestSpeculationService;
import com.projet.ferme.service.subject.SpeculationService;

@RestController
public class FarmingController {
	
	@Autowired
	private SpeculationService speculationService;
	@Autowired
	private HarvestSpeculationService harvestService;
	
	@RequestMapping(value="/api/v1/speculation",method=RequestMethod.POST)
	public Map<String,Object> addFarming(@RequestBody Speculation speculation) {
			return speculationService.add(speculation);
		
	}
	
	@RequestMapping(value="/api/v1/speculation",method=RequestMethod.GET)
	public Map<String,Object> getFarming() {
		return speculationService.findAll();
	}
	
	@RequestMapping(value="/api/v1/speculation/byName", method=RequestMethod.GET)
	public Map<String,Object> getByName(@RequestParam String name){
		return speculationService.findByName(name);
	}
	
	@RequestMapping(value="/api/v1/speculation/byPresent", method=RequestMethod.GET)
	public Map<String,Object> getByPresent(@RequestParam boolean present){
		return speculationService.findByPresent(present);
	}
	
	@RequestMapping(value = "/api/v1/planting/speculation/{id}", method = RequestMethod.GET)
	public Map<String,Object> speculationByPlantingId(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return speculationService.findByPlanting(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw  new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/seed/speculation/{id}", method = RequestMethod.GET)
	public Map<String,Object> speculationBySeedId(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return speculationService.findBySeed(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}	
	}
	
	@RequestMapping(value="/api/v1/speculation",method=RequestMethod.PUT)
	public Map<String,Object> putFarming(@RequestBody Speculation speculation) {
			return speculationService.put(speculation);
		
	}
	
	// Don't use this way If you want to delete a item use /api/v1/speculations/{id}
	@RequestMapping(value="/api/v1/speculation/{id}",method=RequestMethod.DELETE)
	public Map<String,Object> deleteFarming(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return speculationService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/speculation/active/{id}", method = RequestMethod.GET)
	public Map<String,Object> activeSpeculation(@PathVariable("id") Long id){
		return speculationService.activeSpeculation(id) ;
	}
	
	@RequestMapping(value="/api/v1/speculation/count",method=RequestMethod.GET)
	public Map<String,Object> countFarming() {
		return speculationService.count();
	}
	
	@RequestMapping(value = "/api/v1/harvest/{id}",method = RequestMethod.GET)
	public Map<String,Object> getHavest(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return harvestService.findBySpeculationId(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/harvest",method = RequestMethod.POST)
	public Map<String,Object> addHavest(@RequestBody HarvestSpeculation harvest) {
			return harvestService.add(harvest);
		
	}
	
	@RequestMapping(value = "/api/v1/harvest",method = RequestMethod.PUT)
	public Map<String,Object> putHavest(@RequestBody HarvestSpeculation harvest) {
			return harvestService.put(harvest);
		
	}
	
	@RequestMapping(value = "/api/v1/harvest/{id}",method = RequestMethod.DELETE)
	public Map<String,Object> deleteHavest(@PathVariable("id") Long id) throws NoElementFoundException {
			try {
				return harvestService.delete(id);
			} catch (NoElementFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NoElementFoundException();
			}
		
	}

	@RequestMapping(value = "/api/v1/harvest", method = RequestMethod.GET)
	public Map<String,Object> harvestingByMe(){
		return harvestService.doByMe();
	}
}
