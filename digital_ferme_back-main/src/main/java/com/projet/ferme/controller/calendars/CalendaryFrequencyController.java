package com.projet.ferme.controller.calendars;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.calendars.CalendarFrequency;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.service.calendars.CalendaryFrequencyService;

@RestController
public class CalendaryFrequencyController {

	@Autowired
	private CalendaryFrequencyService service;
	
	@RequestMapping(value = "/api/v1/frequence/calendar/speculation", method = RequestMethod.POST)
	public Map<String, Object> postCalendarSpeculation(@RequestBody CalendarFrequency cal)  throws NoElementAddException{
		try {
			return service.addSpeculation(cal);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();

		}
		
	}
	
	@RequestMapping(value = "/api/v1/frequence/calendar/poultry", method = RequestMethod.POST)
	public Map<String, Object> postCalendarPoultry(@RequestBody CalendarFrequency cal) throws NoElementAddException{
		try {
			return service.addPoultry(cal);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
		
	}
	
	@RequestMapping(value = "/api/v1/frequence/calendar/seed", method = RequestMethod.POST)
	public Map<String, Object> postSeedFrequence(@RequestBody CalendarFrequency cal) throws NoElementAddException{
		try {
			return  service.addSeedCalendarFrequence(cal);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/frequence/calendar/category/poultry", method = RequestMethod.POST)
	public Map<String, Object> postCategoryPoultry(@RequestBody CalendarFrequency cal) throws NoElementAddException{
	    try {
			return  service.addCategoryPoultryMin(cal);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
	}	
	
	@RequestMapping(value = "/api/v1/frequence/calendar/category/tree", method = RequestMethod.POST)
	public Map<String, Object> postCategoryTree(@RequestBody CalendarFrequency cal) throws NoElementAddException{
		try {
			return  service.addCategoryTreeMin(cal);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/frequence/calendar/category/fish", method = RequestMethod.POST)
	public Map<String, Object> postCategorfish(@RequestBody CalendarFrequency cal) throws NoElementAddException{
		try {
			return  service.addCategoryFishMin(cal);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
		
	}
	
	@RequestMapping(value = "/api/v1/frequence/calendar/category/cattle", method = RequestMethod.POST)
	public Map<String, Object> postCategoryCattle(@RequestBody CalendarFrequency cal) throws NoElementAddException{
		try {
			return  service.addCategoryCattleMin(cal);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
		
	}
}
