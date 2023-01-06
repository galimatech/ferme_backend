package com.projet.ferme.controller.calendars;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.projet.ferme.entity.calendars.CalendaryCattle;
import com.projet.ferme.entity.calendars.CalendaryFish;
import com.projet.ferme.entity.calendars.CalendaryPoultry;
import com.projet.ferme.entity.calendars.CalendarySpeculation;
import com.projet.ferme.entity.calendars.CalendaryTree;
import com.projet.ferme.entity.calendars.CattleCalendaryMin;
import com.projet.ferme.entity.calendars.FishCalendaryMin;
import com.projet.ferme.entity.calendars.PoultryCalendaryMin;
import com.projet.ferme.entity.calendars.SpeculationCalendaryMin;
import com.projet.ferme.entity.calendars.TreeCalendaryMin;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.calendars.CalendaryCattleService;
import com.projet.ferme.service.calendars.CalendaryFishService;
import com.projet.ferme.service.calendars.CalendaryPoultryService;
import com.projet.ferme.service.calendars.CalendarySpeculationService;
import com.projet.ferme.service.calendars.CalendaryTreeService;

@RestController
public class CalendaryController {

	@Autowired
	private CalendarySpeculationService calendarySpeculationService;
	@Autowired
	private CalendaryCattleService calendaryCattleService;	
	@Autowired
	private CalendaryFishService calendaryFishService;
	@Autowired
	private CalendaryPoultryService calendaryPoultryService;
	@Autowired
	private CalendaryTreeService calendaryTreeService;
	
	@RequestMapping(value = "/api/v1/add/calendar/speculation",method = RequestMethod.POST)
	public Map<String, Object> addCalendarySpeculation(@RequestBody CalendarySpeculation calendar){

			return calendarySpeculationService.add(calendar);
		
	}
	
	@RequestMapping(value="/api/v1/calendar/speculation/{id}",method=RequestMethod.GET)
	public Map<String, Object> getCalendarySpeculation(@PathVariable("id") Long id)  throws NoElementFoundException{
		try {
			return calendarySpeculationService.findBySpeculationId(id);
			
} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	  }
	
	@RequestMapping(value = "/api/v1/add/calendar/cattle",method = RequestMethod.POST)
	public Map<String, Object> addCalendaryCattle(@RequestBody CalendaryCattle calendar) throws NoElementAddException{
		try {
			return calendaryCattleService.add(calendar);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
		
	}
	
	@RequestMapping(value="/api/v1/calendar/cattle/{id}",method=RequestMethod.GET)
	public Map<String, Object> getCalendaryCattle(@PathVariable("id") Long id)  throws NoElementFoundException{
		try {
			return calendaryCattleService.findByCatteId(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	  }
	
	@RequestMapping(value = "/api/v1/add/calendar/fish",method = RequestMethod.POST)
	public Map<String, Object> addCalendaryCattle(@RequestBody CalendaryFish cal) {
		
			return calendaryFishService.add(cal);
			
	}
	
	@RequestMapping(value="/api/v1/calendar/fish/{id}",method=RequestMethod.GET)
	public  Map<String, Object> getCalendaryFish(@PathVariable("id") Long id)  throws NoElementFoundException{
		try {
			return calendaryFishService.findByFish(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	  }
	
	@RequestMapping(value = "/api/v1/add/calendar/poultry",method = RequestMethod.POST)
	public  Map<String, Object> addCalendaryPoultry(@RequestBody CalendaryPoultry calendar) throws NoElementAddException{
		
		try {
			return calendaryPoultryService.add(calendar);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
	}	
	
	@RequestMapping(value="/api/v1/calendar/poultry/{id}",method=RequestMethod.GET)
	public Map<String, Object> getCalendaryPoultry(@PathVariable("id") Long id)  throws NoElementFoundException{
		try {
			return calendaryPoultryService.findByPoultryId(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	  }
	
	@RequestMapping(value = "/api/v1/add/calendar/tree",method = RequestMethod.POST)
	public  Map<String, Object> addCalendaryTree(@RequestBody CalendaryTree cal) {
		
			return calendaryTreeService.add(cal);
			
	}
	
	@RequestMapping(value="/api/v1/calendar/tree/{id}",method=RequestMethod.GET)
	public  Map<String, Object> getCalendaryTree(@PathVariable("id") Long id)  throws NoElementFoundException{
		try {
			return calendaryTreeService.findByTreeId(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	  }
	
	@RequestMapping(value="/api/v1/min/calendar/cattle",method=RequestMethod.POST)
	public  Map<String, Object>  addMinCalendaryCattle(@RequestBody CattleCalendaryMin min) throws NoElementAddException{
		
		try {
			return calendaryCattleService.addMin(min);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}
	}
	
	@RequestMapping(value="/api/v1/min/calendar/cattle/{id}",method=RequestMethod.DELETE)
	public Map<String,Object>  deleteMinCalendaryCattle(@PathVariable("id") Long id) throws NoElementFoundException{
		
		try {
			return  calendaryCattleService.deleteMin(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	}
	
	@RequestMapping(value="/api/v1/min/calendar/fish",method=RequestMethod.POST)
	public  Map<String, Object>  addMinCalendaryFish(@RequestBody FishCalendaryMin min){
		
		
			return calendaryFishService.addMin(min);
	}
	
	@RequestMapping(value="/api/v1/min/calendar/fish/{id}",method=RequestMethod.DELETE)
	public Map<String, Object>  deleteMinCalendaryFish(@PathVariable("id") Long id) throws NoElementFoundException{
		
		try {
			return calendaryFishService.deleteMin(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	}
	
	@RequestMapping(value="/api/v1/min/calendar/poultry",method=RequestMethod.POST)
	public Map<String, Object>  addMinCalendaryPoultry(@RequestBody PoultryCalendaryMin min) {
	
			return calendaryPoultryService.addMin(min);
	}
	
	@RequestMapping(value="/api/v1/min/calendar/poultry/{id}",method=RequestMethod.DELETE)
	public  Map<String, Object>  deleteMinCalendaryPoultry(@PathVariable("id") Long id) throws NoElementFoundException{
		
		try {
			return calendaryPoultryService.deleteMin(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	}
	
	@RequestMapping(value="/api/v1/min/calendar/speculation",method=RequestMethod.POST)
	public Map<String, Object>  addMinCalendarySpeculation(@RequestBody SpeculationCalendaryMin min) throws NoElementAddException{
		
			return calendarySpeculationService.addMin(min);
			
	}
	
	@RequestMapping(value="/api/v1/min/calendar/speculation/{id}",method=RequestMethod.DELETE)
	public Map<String, Object>  deleteMinCalendarySpeculation(@PathVariable("id") Long id) throws NoElementFoundException{
		
		try {
			return calendarySpeculationService.deleteMin(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	}
	
	@RequestMapping(value="/api/v1/min/calendar/tree",method=RequestMethod.POST)
	public Map<String, Object>  addMinCalendaryTree(@RequestBody TreeCalendaryMin min) throws NoElementAddException{

			return calendaryTreeService.addMin(min);
	}
	
	@RequestMapping(value="/api/v1/min/calendar/tree/{id}",method=RequestMethod.DELETE)
	public Map<String, Object>  addMinCalendaryTree(@PathVariable("id") Long id) throws NoElementFoundException{
		
		try {
			return calendaryTreeService.deleteMin(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	}
	
	@RequestMapping(value="/api/v1/delete/calendar/cattle/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteCattle(@PathVariable("id") Long id) throws NoElementFoundException{
		
		try {
			return calendaryCattleService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	}
	
	@RequestMapping(value="/api/v1/delete/calendar/poultry/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> deletePoultry(@PathVariable("id") Long id) throws NoElementFoundException{
		
		try {
			return calendaryPoultryService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value="/api/v1/delete/calendar/speculation/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteSpeculation(@PathVariable("id") Long id) throws NoElementFoundException{

		 
		 try {
			return calendarySpeculationService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	}
	
	@RequestMapping(value="/api/v1/delete/calendar/tree/{id}", method=RequestMethod.DELETE)
	public  Map<String, Object> deleteTree(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return calendaryTreeService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value="/api/v1/delete/calendar/fish/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteFish(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return calendaryFishService.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/make/speculation/{id}",method = RequestMethod.GET)
	public Map<String, Object> updateCalendarySpeculation(@PathVariable("id") Long id){
			return calendarySpeculationService.makeTrue(id);
		
	}
	
	@RequestMapping(value = "/api/v1/giveUp/speculation",method = RequestMethod.POST)
	public Map<String, Object> giveUpSpeculation(@RequestBody CalendarySpeculation cal){
			return calendarySpeculationService.giveUpTrue(cal);
		
	}
	
	@RequestMapping(value = "/api/v1/make/tree/{id}",method = RequestMethod.GET)
	public  Map<String, Object> updateCalendaryTree(@PathVariable("id") Long id){
			return calendaryTreeService.makeTrue(id);
		
	}
	
	@RequestMapping(value = "/api/v1/make/cattle/{id}",method = RequestMethod.GET)
	public Map<String, Object> updateCalendaryCattle(@PathVariable("id") Long id){
			return calendaryCattleService.makeTrue(id);
		
	}
	
	@RequestMapping(value = "/api/v1/make/fish/{id}",method = RequestMethod.GET)
	public Map<String, Object> updateCalendaryFish(@PathVariable("id") Long id){
			return calendaryFishService.makeTrue(id);
		
	}
	
	@RequestMapping(value = "/api/v1/make/poultry/{id}",method = RequestMethod.GET)
	public Map<String, Object> updateCalendaryPoultry(@PathVariable("id") Long id){
	
			return calendaryPoultryService.makeTrue(id);

		
	}
	
	@RequestMapping(value = "/api/v1/calendary/category/poultry/min/{id}",method = RequestMethod.GET)
	public Map<String, Object> getMinCalendarPoultry(@PathVariable("id") Long id) throws NoElementFoundException{ 
		try {
			return calendaryPoultryService.findByCategory(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/calendary/category/cattle/min/{id}",method = RequestMethod.GET)
	public Map<String, Object> getMinCalendarCattle(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return calendaryCattleService.findByCategory(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/calendary/category/fish/min/{id}",method = RequestMethod.GET)
	public Map<String, Object> getMinCalendarFish(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return calendaryFishService.findByCategory(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/calendary/category/tree/min/{id}",method = RequestMethod.GET)
	public Map<String, Object> getMinCalendarTree(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return  calendaryTreeService.findByCategory(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/calendary/category/speculation/min/{id}",method = RequestMethod.GET)
	public Map<String, Object> getMinCalendarSpeculation(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return calendarySpeculationService.findBySeed(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}

	@RequestMapping(value = "/api/v1/calendar/speculation/speculationByDate", method = RequestMethod.GET)
	public Map<String, Object> getSpeculationByDate(){
		//System.out.println("Ok events of day for speculation");
		return calendarySpeculationService.findSpeculationByDate();
	}

	@RequestMapping(value = "/api/v1/calendar/poultry/poultryByDate", method = RequestMethod.GET)
	public Map<String, Object> getPoultryByDate(){
		//System.out.println("Ok events of day for poultry");
		return calendaryPoultryService.findPoultryByDate();
	}

	@RequestMapping(value = "/api/v1/giveUp/poultry",method = RequestMethod.POST)
	public Map<String, Object> giveUpPoultry(@RequestBody CalendaryPoultry cal){

			return calendaryPoultryService.giveUpTrue(cal);
		
	}
}
