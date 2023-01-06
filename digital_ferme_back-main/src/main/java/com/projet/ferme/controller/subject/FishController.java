package com.projet.ferme.controller.subject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.calendars.CalendaryFish;
import com.projet.ferme.entity.subject.Fish;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.calendars.CalendaryFishService;
import com.projet.ferme.service.subject.FishService;

@RestController
public class FishController {
	
	@Autowired
	private FishService fishService;
	@Autowired
	private CalendaryFishService calendaryFishService;

	@RequestMapping(value="/api/v1/fish", method=RequestMethod.POST)
	public Map<String, Object> add(@RequestBody Fish fish) {
			return fishService.add(fish);
		
	}
	
	@RequestMapping(value="/api/v1/fish", method=RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody Fish fish) {
			return fishService.put(fish);
		
	}
	
	@RequestMapping(value="/api/v1/fish", method=RequestMethod.GET)
	public Map<String, Object> get(){
		return fishService.get();
	}
	
	@RequestMapping(value="api/v1/fish/byName",method=RequestMethod.GET)
	public Map<String, Object> getFish(@RequestParam String name){
		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = fishService.findByName(name);
	
		return returnValues;
		
	}
	
	@RequestMapping(value="api/v1/fish/byPresent",method=RequestMethod.GET)
	public Map<String, Object> getFish(@RequestParam Boolean present){
		
		Map<String,Object> returnValues = new HashMap<String, Object>();
		returnValues = fishService.findByPresent(present);
	
		return returnValues;
		
	}
	
	// Don't use this route, to delete use /api/v1/fishs/{id}
	@RequestMapping(value="/api/v1/fish/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return fishService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "api/v1/bowl/fish/{id}", method = RequestMethod.GET)
	public Map<String, Object> fishByBowl(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return fishService.fishByBowl(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "api/v1/category/fish/{id}", method = RequestMethod.GET)
	public Map<String, Object> fishByCategory(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return fishService.findByCategory(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value="/api/v1/calendar/fish", method=RequestMethod.POST)
	public Map<String, Object> addCalendar(CalendaryFish calendar) {
			return calendaryFishService.add(calendar);
		
	}
	

	
	@RequestMapping(value="/api/v1/fish/count", method=RequestMethod.GET)
	public Map<String, Object> count(){
		return fishService.count();
	}
}
