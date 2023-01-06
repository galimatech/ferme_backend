package com.projet.ferme.service.calendars; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendarySpeculation;
import com.projet.ferme.entity.calendars.SpeculationCalendaryMin;
import com.projet.ferme.entity.category.Seed;
import com.projet.ferme.entity.subject.Speculation;
import com.projet.ferme.entity.utils.NewDate;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendarySpeculationRepository;
import com.projet.ferme.repository.calendars.SpeculationCalendaryMinRepository;
import com.projet.ferme.repository.category.SeedRepository;
import com.projet.ferme.repository.subject.SpeculationRepository;

@Service
public class CalendarySpeculationService {

	@Autowired
	private CalendarySpeculationRepository calendarySpeculationRepository;
	@Autowired
	private SpeculationCalendaryMinRepository minRepository;
	@Autowired
	private SeedRepository seedRepository;
	@Autowired
	private SpeculationRepository speculationRepository;
	

	public Map<String, Object> add(CalendarySpeculation calendaryFarming){
		boolean stateGiveUp=calendaryFarming.getId()==null? false:calendaryFarming.getGiveUp();
		calendaryFarming.setGiveUp(stateGiveUp);
		CalendarySpeculation newCalendaryFarming = calendarySpeculationRepository.save(calendaryFarming);
		Map<String, Object> returnValues = new HashMap<String,Object>();
		
		if(newCalendaryFarming == null) {
			returnValues.put("success", false);
			returnValues.put("calendar", newCalendaryFarming);
		}else {
				returnValues.put("success", true);
				returnValues.put("calendar", newCalendaryFarming);
		}
		
		return returnValues;
	}
	
	public Map<String, Object> findBySpeculationId(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		List <CalendarySpeculation> calendars = calendarySpeculationRepository.getBySpeculation_id(id);
		Speculation speculation = speculationRepository.findById(id).get();	
		returnValues.put("success", true);
		returnValues.put("calendars", calendars);
		returnValues.put("speculation", speculation);	
		return returnValues;
	}
	
	public Map<String, Object> put(CalendarySpeculation calendaryFarming) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		CalendarySpeculation newCalendaryFarming = calendarySpeculationRepository.save(calendaryFarming);
		returnValues.put("success", true);
		returnValues.put("calendar", newCalendaryFarming);
		return returnValues;
	}
	
	public Map<String, Object> makeTrue(Long id) {
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		CalendarySpeculation calendarySpeculation =  calendarySpeculationRepository.getById(id);
		if(calendarySpeculation == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Enregistrement introuvable");
		}else {
			calendarySpeculation.setUpdatedOn(NewDate.getDateStatic());
			calendarySpeculation.setMake(true);
			calendarySpeculation = calendarySpeculationRepository.save(calendarySpeculation);
			returnValues.put("success", true);
			returnValues.put("message", "Enregistrement réussi");
		}
		
		return returnValues;
	}
	
	public Map<String, Object> giveUpTrue(CalendarySpeculation cal) {
		Map<String, Object> map = new HashMap<String, Object>();
		CalendarySpeculation oldCalendarySpeculation = calendarySpeculationRepository.getById(cal.getId());
		if(oldCalendarySpeculation == null) {
			map.put("success", false);
			map.put("message", "Ce programme est supprimé");
		}else {
			oldCalendarySpeculation.setGiveUp(true);
			oldCalendarySpeculation.setDescription(cal.getDescription());
			oldCalendarySpeculation.setUpdatedOn(NewDate.getDateStatic());
			calendarySpeculationRepository.save(oldCalendarySpeculation);
			map.put("success", true);
			map.put("message", "Modofier avec succé");
		}
		return map;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		calendarySpeculationRepository.deleteById(id);
		returnValues.put("success", true);
		return returnValues;
	}
	
	public Map<String, Object> addMin(SpeculationCalendaryMin min) {
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		SpeculationCalendaryMin minCalendar = minRepository.save(min);
		
		if(minCalendar == null) {
			returnValues.put("success", false);
			returnValues.put("minCalendar", minCalendar);
		}else {

			returnValues.put("success", true);
			returnValues.put("minCalendar", minCalendar);
			returnValues.put("seed", minCalendar.getSeed());
		}
		
		return returnValues;
	}
	
	public Map<String, Object> deleteMin(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String,Object>();
		minRepository.deleteById(id);
		return returnValues;
	}
	
	public Map<String, Object> findBySeed(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<SpeculationCalendaryMin> calendaryMins = minRepository.findBySeedId(id);
		Seed seed = seedRepository.findById(id).get();
		
		map.put("success", true);
		map.put("calendaryMins", calendaryMins);
		map.put("seed", seed);
		
		return map;
	}
	
	public Map<String, Object> findSpeculationByDate(){
		Map<String, Object> returnValues = new HashMap<String,Object>();
		List <CalendarySpeculation> calendars = calendarySpeculationRepository.findAll();

		calendars = calendars.stream().
		filter(calendar -> calendar.getGiveUp().equals(false) && calendar.getMake().equals(false)).
		collect(Collectors.toList());

		 
			returnValues.put("success", true);
			returnValues.put("calendars", calendars);   

		return returnValues;

}

}
	
