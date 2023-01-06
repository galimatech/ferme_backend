package com.projet.ferme.service.subject;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendaryPoultry;
import com.projet.ferme.entity.calendars.PoultryCalendaryMin;
import com.projet.ferme.entity.category.PoultryCategory;
import com.projet.ferme.entity.homesubject.ChickenCoop;
import com.projet.ferme.entity.subject.Poultry;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendaryPoultryRepository;
import com.projet.ferme.repository.calendars.PoultryCalendaryMinRepository;
import com.projet.ferme.repository.homesubject.ChikenCoopRepository;
import com.projet.ferme.repository.subject.PoultryRepository;
import com.projet.ferme.service.category.CategoryPoultryService;

@Service
public class PoultryService {

	@Autowired
	PoultryRepository poultryRepository;
	@Autowired
	private PoultryCalendaryMinRepository minRepository;
	@Autowired
	private CalendaryPoultryRepository calendaryPoultryRepository;
	@Autowired
	private ChikenCoopRepository coopRepository;
	@Autowired
	private MatriculeService matricule;
	@Autowired
	private CategoryPoultryService categoryPoultryService;
	
	public Map<String, Object> add(Poultry poultry) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		poultry.setName(matricule.getName(poultry.getCategory().getName()));
		Poultry newPoultry = poultryRepository.save(poultry);
		
		if(newPoultry == null) {
			returnValues.put("success", false);
			returnValues.put("poultry", newPoultry);
		}else {
			java.util.Date date=new java.util.Date();
			Date sqlStartDate = new Date(date.getTime());
			List<PoultryCalendaryMin> mins = minRepository.findByCategoryId(newPoultry.getCategory().getId());
			for(PoultryCalendaryMin min: mins) {
				CalendaryPoultry cal = new  CalendaryPoultry();
				Calendar c = Calendar.getInstance();
				c.setTime(newPoultry.getDateOfEntry());
				c.add(Calendar.DATE,min.getOld());
				cal.setPoultry(newPoultry);
				cal.setCalendaryName(min.getName());
				Date x = new Date(c.getTimeInMillis());
				cal.setDate(x);
				cal.setCreatedOn(sqlStartDate);
				cal.setIntervention(min.getIntervention());
				cal.setMake(false);
				cal.setGiveUp(false);
				cal.setUpdatedOn(null);
				cal.setId(null);
				
				calendaryPoultryRepository.save(cal);
				
				ChickenCoop coop = newPoultry.getChickenCoop();
				
				coop.setFree(false);
				
				coopRepository.save(coop);
			}
			returnValues.put("success", true);
			returnValues.put("poultry", newPoultry);
		}
		
		return returnValues;
	}
	
	public Map<String, Object> findAll(){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Poultry> newPoultrys = poultryRepository.findAll();
		
		returnValues.put("success", true);
		returnValues.put("poultry", newPoultrys);
		
		return returnValues;
	}
	
	public Map<String, Object> findByPresent(boolean b) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Poultry> poultrys = poultryRepository.findByPresent(b);
		
		returnValues.put("success", true);
		returnValues.put("poultrys", poultrys);
		
		return returnValues;
	}
	
	public Map<String, Object> findByName(String name){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Optional<Poultry> poultry = poultryRepository.findByName(name);
		
		if (poultry == null) {
			returnValues.put("success", false);
			returnValues.put("poultry", poultry);
		}else {
			returnValues.put("success", true);
			returnValues.put("poultry", poultry);
		}
		
		return returnValues;
	}
	
	public Map<String, Object> put(Poultry poultry) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Poultry newPoultry = poultryRepository.save(poultry);
		
		if(newPoultry == null) {
			returnValues.put("success", false);
			returnValues.put("poultry", newPoultry);
		}else {
			returnValues.put("success", true);
			returnValues.put("poultry", newPoultry);
		}
		
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
	
		Map<String, Object> returnValues = new HashMap<String, Object>();
		poultryRepository.deleteById(id);
		returnValues.put("success", true);
		
		return returnValues;
	}
	

	
	public Map<String, Object> count() {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		
		int isPresent = poultryRepository.countByPresent(true);
		int isMissing = poultryRepository.countByPresent(false);
		
		returnValues.put("success", true);
		returnValues.put("present", isPresent);
		returnValues.put("missing",isMissing);
		
		return returnValues;
	}
	
	public Map<String, Object> findByCoop(Long id)  throws NoElementFoundException{
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Poultry> poultrysList = poultryRepository.ChickenCoop_id(id);
		Optional<ChickenCoop> coopOptional = coopRepository.findById(id);
		if (coopOptional!=null) {
			returnValues.put("success", true);
			returnValues.put("coop", coopOptional.get());
			returnValues.put("poultrys", poultrysList);
			return returnValues;
			
		} else {
			throw new NoElementFoundException();
			
		}	
	
	}
	
	public Map<String, Object> findByCategory(Long id) throws NoElementFoundException{

		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Poultry> poultrysList = poultryRepository.findByCategory_id(id);
		PoultryCategory category = categoryPoultryService.findById(id);
		if(category!=null){
		
		returnValues.put("success", true);
		returnValues.put("category", category);
		returnValues.put("poultrys", poultrysList);
		return returnValues;

	}else{
		throw new NoElementFoundException();}
	}
}
