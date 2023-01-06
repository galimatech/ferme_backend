package com.projet.ferme.service.calendars;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendaryCattle;
import com.projet.ferme.entity.calendars.CattleCalendaryMin;
import com.projet.ferme.entity.category.CattleCategory;
import com.projet.ferme.entity.subject.Cattle;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendaryCattleRepository;
import com.projet.ferme.repository.calendars.CattleCalendaryMinRepository;
import com.projet.ferme.repository.category.CattleCategoryRepository;
import com.projet.ferme.repository.subject.CattleRepository;

@Service
public class CalendaryCattleService {
	
	@Autowired
	private CalendaryCattleRepository calendaryCattleRepository;
	@Autowired
	private CattleCalendaryMinRepository minRepository;
	@Autowired
	private CattleCategoryRepository categoryRepository;
	@Autowired
	private CattleRepository cattleRepository;
	
	
	public Map<String,Object> add(CalendaryCattle newCalendar)  throws NoElementAddException{
		newCalendar.setGiveUp(false);
		CalendaryCattle calendar = calendaryCattleRepository.save(newCalendar);
		Map<String, Object> returnValues = new HashMap<String, Object>();
		
		returnValues.put("success", true);
		returnValues.put("calendar", calendar);
		
		return returnValues;
	}
	
	public Map<String,Object> findByCatteId(Long id) throws NoElementFoundException{
		
		List<CalendaryCattle> calendars = calendaryCattleRepository.findByCattle_id(id);
		Cattle cattle = cattleRepository.findById(id).get();
		Map<String, Object> returnValues = new HashMap<String,Object>();
		
		returnValues.put("success", true);
		returnValues.put("calendars", calendars);
		returnValues.put("cattle", cattle);
			
		return returnValues;
	


	}
	
	
	public Map<String, Object> put(CalendaryCattle calendar) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		
		CalendaryCattle calendary = calendaryCattleRepository.save(calendar);
		returnValues.put("success", true);
		returnValues.put("calendar", calendary);
		
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		calendaryCattleRepository.deleteById(id);
		returnValues.put("success", true);
		
		return returnValues;
	}
	
	public Map<String, Object> addMin(CattleCalendaryMin min) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		
		CattleCalendaryMin minCalendar = minRepository.save(min);
		
		if(minCalendar == null) {
			returnValues.put("success", false);
			returnValues.put("mincalendar",minCalendar);
		}else {

			returnValues.put("success", true);
			returnValues.put("minCalendar",minCalendar);
			returnValues.put("category",minCalendar.getCategory());
		}
		
		return returnValues;
	}
	
	public Map<String, Object> deleteMin(Long id) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		minRepository.deleteById(id);
		returnMap.put("success", true);
		return returnMap ;
	}
	
	public Map<String, Object> makeTrue(Long id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		CalendaryCattle calendaryCattle = calendaryCattleRepository.getById(id);
		if(calendaryCattle == null) {
			returnMap.put("success", false);
			returnMap.put("message", "Enregistrement introuvable");
		}else {
			java.util.Date date=new java.util.Date();
			Date sqlStartDate = new Date(date.getTime());
			calendaryCattle.setUpdatedOn(sqlStartDate);
			calendaryCattle.setMake(true);
			calendaryCattle = calendaryCattleRepository.save(calendaryCattle);
			returnMap.put("success", true);
			returnMap.put("message", "Enregistrement réussi");
		}
		return returnMap ;
	}
	
	public Map<String, Object> findByCategory(Long id)   throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<CattleCalendaryMin> calendaryMins = minRepository.findByCategoryId(id);
		CattleCategory category = categoryRepository.findById(id).get();
	
		map.put("success", true);
		map.put("calendaryMins", calendaryMins);
		map.put("category", category);
		
		return map;
	
	}
		
}
