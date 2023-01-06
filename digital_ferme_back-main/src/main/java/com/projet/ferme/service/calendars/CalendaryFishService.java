package com.projet.ferme.service.calendars;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendaryFish;
import com.projet.ferme.entity.calendars.FishCalendaryMin;
import com.projet.ferme.entity.category.FishCategory;
import com.projet.ferme.entity.subject.Fish;

import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendaryFishRepository;
import com.projet.ferme.repository.calendars.FishCalendaryMinRepository;
import com.projet.ferme.repository.category.FishCategoryRepository;
import com.projet.ferme.repository.subject.FishRepository;

@Service
public class CalendaryFishService {

	@Autowired
	private CalendaryFishRepository calendaryFishRepository ;
	@Autowired
	private FishCalendaryMinRepository minRepository;
	@Autowired
	private FishCategoryRepository categoryRepository;
	@Autowired
	private FishRepository fishRepository;
	
	public Map<String,Object> add(CalendaryFish c) {
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		c.setGiveUp(false);
		CalendaryFish calendar = calendaryFishRepository.save(c);
		
		if(calendar == null) {
			returnValue.put("success", false);
			returnValue.put("calendar", calendar);
		}else {
			returnValue.put("success", true);
			returnValue.put("calendar", calendar);
		}
		
		return returnValue;
	}
	
	public Map<String,Object> put(CalendaryFish c) throws NoElementFoundException{
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		CalendaryFish calendar;
		
		calendar = calendaryFishRepository.save(c);
		
		if(calendar == null) {
			returnValue.put("success", false);
			returnValue.put("calendar", calendar);
		}else {
			returnValue.put("success", true);
			returnValue.put("calendar", calendar);
		}
		
		return returnValue;
	}
	
	public Map<String, Object> findByFish(Long id)  throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		Fish fish = fishRepository.findById(id).get();
		List<CalendaryFish> calendars = calendaryFishRepository.findByFish_id(id);
		
		map.put("success", true);
		map.put("calendars", calendars);
		map.put("fish", fish);
		
		return map;
	}

	public Map<String,Object> get( ){
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		List<CalendaryFish> calendars;
		
		calendars = calendaryFishRepository.findAll();
		returnValue.put("success", true);
		returnValue.put("calendar", calendars);
		
		return returnValue;
	}
	
	public Map<String,Object> delete(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		
		calendaryFishRepository.deleteById(id);
		returnValue.put("success", true);
		
		return returnValue;
	}
	
	public Map<String,Object> addMin(FishCalendaryMin min){
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		FishCalendaryMin minCalendar = minRepository.save(min);
		
		if(minCalendar == null) {
			returnValue.put("success", false);
			returnValue.put("minCalendar", minCalendar);
		}else {

			returnValue.put("success", true);
			returnValue.put("minCalendar", minCalendar);
			returnValue.put("category", minCalendar.getCategory());
		}
		return returnValue;
	}
	
	public Map<String, Object> deleteMin(Long id) throws NoElementFoundException{
		Map<String, Object> returnValue = new HashMap<String, Object>();
		minRepository.deleteById(id);
		return returnValue;
	}
	
	public Map<String, Object> makeTrue(Long id) {
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		CalendaryFish calendary =  calendaryFishRepository.getById(id);
		
		if(calendary == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Enregistrement introuvable");
		}else {
			java.util.Date date=new java.util.Date();
			Date sqlStartDate = new Date(date.getTime());
			calendary.setUpdatedOn(sqlStartDate);
			calendary.setMake(true);
			calendary = calendaryFishRepository.save(calendary);
			returnValues.put("success", true);
			returnValues.put("message", "Enregistrement réussi");
		}
		
		return returnValues;
	}
	
	public Map<String, Object> findByCategory(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<FishCalendaryMin> calendaryMins = minRepository.findByCategoryId(id);
		FishCategory category = categoryRepository.findById(id).get();
		
		map.put("success", true);
		map.put("calendaryMins", calendaryMins);
		map.put("category", category);
		
		return map;
	}
}
