package com.projet.ferme.service.subject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendarySpeculation;
import com.projet.ferme.entity.calendars.SpeculationCalendaryMin;
import com.projet.ferme.entity.category.Seed;
import com.projet.ferme.entity.homesubject.Planting;
import com.projet.ferme.entity.subject.Speculation;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendarySpeculationRepository;
import com.projet.ferme.repository.calendars.SpeculationCalendaryMinRepository;
import com.projet.ferme.repository.subject.SpeculationRepository;
import com.projet.ferme.service.category.SeedService;
import com.projet.ferme.service.homesubject.PlantingService;

@Service
public class SpeculationService {

	@Autowired
	private SpeculationRepository farmingRepository;
	@Autowired
	private SpeculationCalendaryMinRepository minRepository;
	@Autowired
	private CalendarySpeculationRepository calendarySpeculationRepository;
	@Autowired
	private PlantingService plantingService;
	@Autowired
	private MatriculeService matricule;
	@Autowired
	private SeedService seedService;
	
	public Map<String, Object> add(Speculation speculation)  {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		speculation.setName(matricule.getName(speculation.getSeed().getSeedName()));
		LocalDate transplantingDate = speculation.getSeedDate().toLocalDate().plusDays(speculation.getSeed().getTransplantingOld());
		speculation.setTransplantingDate(Date.valueOf(transplantingDate));
		Speculation newFarming = farmingRepository.save(speculation);
		
		if(newFarming == null){
			returnValues.put("success", false);
			returnValues.put("speculation", newFarming);
		}else {
			java.util.Date date=new java.util.Date();
			Date sqlStartDate = new Date(date.getTime());
			List<SpeculationCalendaryMin> mins = minRepository.findBySeedId(newFarming.getSeed().getId());
			for(SpeculationCalendaryMin min: mins) {
				CalendarySpeculation cal = new  CalendarySpeculation();
				Calendar c = Calendar.getInstance();
				c.setTime(newFarming.getTransplantingDate());
				c.add(Calendar.DATE,min.getOld());
				cal.setSpeculation(newFarming);
				cal.setCalendaryName(min.getName());
				Date x = new Date(c.getTimeInMillis());
				cal.setDate(x);
				cal.setCreatedOn(sqlStartDate);
				cal.setIntervention(min.getIntervention());
				cal.setMake(false);
				cal.setGiveUp(false);
				cal.setUpdatedOn(null);
				cal.setId(null);
				
				calendarySpeculationRepository.save(cal);
				
			}
			returnValues.put("success", true);
			returnValues.put("speculation", newFarming);
		}
		
		return returnValues;
	}
	
	public Map<String, Object> findAll() {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Speculation> farmings = farmingRepository.findAll();
		
		returnValues.put("success", true);
		returnValues.put("speculation", farmings);
		
		return returnValues;
	}
	
	public Map<String, Object> findByName(String name){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Optional<Speculation> speculation = farmingRepository.findByName(name) ;
		
		if(speculation == null) {
			returnValues.put("success", false);
			returnValues.put("speculation", speculation);
		}else {
			returnValues.put("success", true);
			returnValues.put("speculation", speculation);
		}
			
		return returnValues;
	}
	
	public Map<String, Object> findByPresent(boolean b){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Speculation> speculations = farmingRepository.findByPresent(b) ;
		
			returnValues.put("success", true);
			returnValues.put("speculations", speculations);
			
			return returnValues;
	}
	
	public Map<String, Object> findByPlanting(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Speculation> speculations = farmingRepository.findByPlanting_id(id);
		Planting planting = plantingService.findById(id);
			map.put("success", true);
			map.put("planting", planting);
			map.put("speculations", speculations);
			
			return map;
	}
	
	public Map<String, Object> findBySeed(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Speculation> speculations = farmingRepository.findBySeed_id(id);
		Seed seed = seedService.findById(id);
			map.put("success", true);
			map.put("seed", seed);
			map.put("speculations", speculations);
				
			return map;
	}
	
	public Map<String, Object> put(Speculation speculation) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Speculation newFarming = farmingRepository.save(speculation);
		
		if(newFarming == null){
			returnValues.put("success", false);
			returnValues.put("speculation", newFarming);
		}else {
			returnValues.put("success", true);
			returnValues.put("speculation", newFarming);
		}
		
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		farmingRepository.deleteById(id);
		returnValues.put("success", true);		
		return returnValues;
	}
	
	public Map<String, Object> count() {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		
		int isPresent = farmingRepository.countByPresent(true);
		int isMissing = farmingRepository.countByPresent(false);
		
		returnValues.put("success", true);
		returnValues.put("present", isPresent);
		returnValues.put("missing",isMissing);
		
		return returnValues;
	}
	
	public Map<String, Object> activeSpeculation(Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Optional<Speculation> speculation = farmingRepository.findById(id);
		if (speculation.isEmpty()) {
			map.put("success", false);
			map.put("message", "L'enregistrement est déja supprimmé");
		}else {
			speculation.get().setPresent(!speculation.get().isPresent());
			speculation.get().setUpdatedOn(getDate());
			farmingRepository.save(speculation.get());
			map.put("success", true);
			map.put("speculation", speculation.get());
			map.put("message", "Changé avec succé");
		}
		return map;
	}
	
	private Date getDate() {
		java.util.Date date=new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		return sqlStartDate;
	}
}
