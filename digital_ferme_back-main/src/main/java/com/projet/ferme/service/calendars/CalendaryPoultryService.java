package com.projet.ferme.service.calendars;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendaryPoultry;
import com.projet.ferme.entity.calendars.PoultryCalendaryMin;
import com.projet.ferme.entity.category.PoultryCategory;
import com.projet.ferme.entity.subject.Poultry;
import com.projet.ferme.entity.utils.NewDate;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendaryPoultryRepository;
import com.projet.ferme.repository.calendars.PoultryCalendaryMinRepository;
import com.projet.ferme.repository.category.PoultryCategoryRepository;
import com.projet.ferme.repository.subject.PoultryRepository;

@Service
public class CalendaryPoultryService {
	
	@Autowired
	private CalendaryPoultryRepository calendaryPoultryRepository;
	@Autowired
	private PoultryCalendaryMinRepository minRepository;
	@Autowired
	private PoultryCategoryRepository categoryRepository;
	@Autowired
	private PoultryRepository poultryRepository;
	
	public Map<String, Object> add(CalendaryPoultry newCalendar) throws NoElementAddException{
		newCalendar.setGiveUp(false);
		CalendaryPoultry calendaryPoultry =  calendaryPoultryRepository.save(newCalendar);
		
		Map<String,Object> returnValues = new HashMap<String,Object>();
		
		returnValues.put("success", true);
		returnValues.put("calendar",calendaryPoultry);
		
		return returnValues;
	}
	
	public Map<String, Object> findByPoultryId(Long id) throws NoElementFoundException{
		
		List<CalendaryPoultry> calendaryPoultry = calendaryPoultryRepository.findByPoultry_id(id);
		Poultry poultry = poultryRepository.findById(id).get();
		Map<String,Object> returnValues = new HashMap<String,Object>();
		
		returnValues.put("success", true);
		returnValues.put("calendars",calendaryPoultry);
		returnValues.put("poultry", poultry);
		
		return returnValues;
	}
	
	public Map<String, Object> put(CalendaryPoultry calendaryPoultry) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		CalendaryPoultry calendar = calendaryPoultryRepository.save(calendaryPoultry);
		returnValues.put("success", true);
		returnValues.put("calendar",calendar);
		
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		calendaryPoultryRepository.deleteById(id);
		returnValues.put("success", true);
		return returnValues;
	}
	
	public Map<String, Object> addMin(PoultryCalendaryMin min) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		PoultryCalendaryMin minCalendar = minRepository.save(min);
		
		if(minCalendar == null) {
			returnValues.put("success", false);
			returnValues.put("minCalendar", minCalendar);
		}else {
			returnValues.put("success", true);
			returnValues.put("minCalendar", minCalendar);
			returnValues.put("category", minCalendar.getCategory());
		}
		return returnValues;
	}
	
	public Map<String, Object> deleteMin(Long id) throws NoElementFoundException{
		Map<String, Object> returnValues = new HashMap<String, Object>();
		minRepository.deleteById(id);
		returnValues.put("success", true);
		returnValues.put("message", "Supprimer avec succé");
		
		return returnValues;
	}
	
	public Map<String, Object> makeTrue(Long id) {
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		CalendaryPoultry calendary =  calendaryPoultryRepository.getById(id);
		
		if(calendary == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Enregistrement introuvable");
		}else {
			java.util.Date date=new java.util.Date();
			Date sqlStartDate = new Date(date.getTime());
			calendary.setUpdatedOn(sqlStartDate);
			calendary.setMake(true);
			calendary = calendaryPoultryRepository.save(calendary);
			returnValues.put("success", true);
			returnValues.put("message", "Enregistrement réussi");
		}
		
		return returnValues;
	}


	public Map<String, Object> giveUpTrue(CalendaryPoultry cal) {
		Map<String, Object> map = new HashMap<String, Object>();
		CalendaryPoultry oldCalendaryPoultry = calendaryPoultryRepository.getById(cal.getId());
		if(oldCalendaryPoultry == null) {
			map.put("success", false);
			map.put("message", "Ce programme est supprimé");
		}else {
			oldCalendaryPoultry.setGiveUp(true);
			oldCalendaryPoultry.setDescription(cal.getDescription());
			oldCalendaryPoultry.setUpdatedOn(NewDate.getDateStatic());
			calendaryPoultryRepository.save(oldCalendaryPoultry);
			map.put("success", true);
			map.put("message", "Modofier avec succé");
		}
		return map;
	}
	
	public Map<String, Object> findByCategory(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<PoultryCalendaryMin> calendaryMins = minRepository.findByCategoryId(id);
		PoultryCategory category = categoryRepository.findById(id).get();
		
		map.put("success", true);
		map.put("calendaryMins", calendaryMins);
		map.put("category",category);
		
		return map;
	}

	public Map<String, Object> findPoultryByDate(){
		Map<String, Object> map = new HashMap<String, Object>();
		//List<CalendaryPoultry> calendars = calendaryPoultryRepository.getPoultryByDate(NewDate.getDateStatic());
		List <CalendaryPoultry> calendars = calendaryPoultryRepository.findAll();

		calendars = calendars.stream().
		filter(calendar -> calendar.getGiveUp().equals(false) && calendar.getMake().equals(false)).
		collect(Collectors.toList());
		map.put("success", true);
		map.put("calendars", calendars);

		return map;


	}
}
