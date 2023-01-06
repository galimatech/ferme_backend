package com.projet.ferme.service.calendars;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendarFrequency;
import com.projet.ferme.entity.calendars.CalendaryPoultry;
import com.projet.ferme.entity.calendars.CalendarySpeculation;
import com.projet.ferme.entity.calendars.CattleCalendaryMin;
import com.projet.ferme.entity.calendars.FishCalendaryMin;
import com.projet.ferme.entity.calendars.PoultryCalendaryMin;
import com.projet.ferme.entity.calendars.SpeculationCalendaryMin;
import com.projet.ferme.entity.calendars.TreeCalendaryMin;
import com.projet.ferme.entity.subject.Poultry;
import com.projet.ferme.entity.subject.Speculation;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.repository.calendars.CalendaryPoultryRepository;
import com.projet.ferme.repository.calendars.CalendarySpeculationRepository;
import com.projet.ferme.repository.calendars.CattleCalendaryMinRepository;
import com.projet.ferme.repository.calendars.FishCalendaryMinRepository;
import com.projet.ferme.repository.calendars.PoultryCalendaryMinRepository;
import com.projet.ferme.repository.calendars.SpeculationCalendaryMinRepository;
import com.projet.ferme.repository.calendars.TreeCalendaryMinRepository;

@Service
public class CalendaryFrequencyService {

	@Autowired
	private CalendarySpeculationRepository calendarySpeculationRepository;
	@Autowired
	private CalendaryPoultryRepository calendaryPoultryRepository;
	@Autowired
	private SpeculationCalendaryMinRepository speculationCalendaryMinRepository;
	@Autowired
	private PoultryCalendaryMinRepository poultryCalendaryMinRepository;
	@Autowired
	private TreeCalendaryMinRepository treeCalendaryMinRepository;
	@Autowired
	private FishCalendaryMinRepository fishCalendaryMinRepository;
	@Autowired
	private CattleCalendaryMinRepository cattleCalendaryMinRepository;

	public Map<String, Object> addSpeculation(CalendarFrequency calendarFrequency) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		Speculation speculation = calendarFrequency.getSpeculation();
		int number = (calendarFrequency.getEnd() - calendarFrequency.getStart())/calendarFrequency.getFrequence();
		java.util.Date date = new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		int saved = 0;
		List<CalendarySpeculation> calendars= new ArrayList<CalendarySpeculation>();
		for (int i = 0; i <= number; i++) {
			
			int dayToAdd = (calendarFrequency.getFrequence() * i ) + calendarFrequency.getStart();
			CalendarySpeculation cal = new  CalendarySpeculation();
			Calendar c = Calendar.getInstance();
			c.setTime(speculation.getSeedDate());
			c.add(Calendar.DATE,dayToAdd);
			cal.setSpeculation(speculation);
			cal.setCalendaryName(calendarFrequency.getCalendaryName());
			Date x = new Date(c.getTimeInMillis());
			cal.setDate(x);
			cal.setCreatedOn(sqlStartDate);
			cal.setIntervention(calendarFrequency.getIntervention());
			cal.setMake(false);
			cal.setUpdatedOn(null);
			cal.setId(null);
			
			CalendarySpeculation resultCalendarySpeculation =  calendarySpeculationRepository.save(cal);
			if (resultCalendarySpeculation != null) {
				calendars.add(resultCalendarySpeculation);
				saved++;
			}
		}
		
		map.put("success", true);
		map.put("message", saved+" sont enregistrés");
		map.put("calendars", calendars);
		
		return map;
	}
	
	public Map<String, Object> addPoultry(CalendarFrequency calendarFrequency) throws NoElementAddException{
		
		Map<String, Object> map = new HashMap<String,Object>();
		Poultry poultry = calendarFrequency.getPoultry();
		int number = (calendarFrequency.getEnd() - calendarFrequency.getStart())/calendarFrequency.getFrequence();
		java.util.Date date = new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		int saved = 0;
		List<CalendaryPoultry> calendars= new ArrayList<CalendaryPoultry>();
		for (int i = 0; i <= number; i++) {
			
			int dayToAdd = (calendarFrequency.getFrequence() * i) + calendarFrequency.getStart() ;
			CalendaryPoultry cal = new  CalendaryPoultry();
			Calendar c = Calendar.getInstance();
			c.setTime(poultry.getDateOfEntry());
			c.add(Calendar.DATE,dayToAdd);
			cal.setPoultry(poultry);
			cal.setCalendaryName(calendarFrequency.getCalendaryName());
			Date x = new Date(c.getTimeInMillis());
			cal.setDate(x);
			cal.setCreatedOn(sqlStartDate);
			cal.setIntervention(calendarFrequency.getIntervention());
			cal.setMake(false);
			cal.setUpdatedOn(null);
			cal.setId(null);
			
			CalendaryPoultry resultCalendarySpeculation =  calendaryPoultryRepository.save(cal);
			if (resultCalendarySpeculation != null) {
				calendars.add(resultCalendarySpeculation);
				saved++;
			}
		}
		
		map.put("success", true);
		map.put("message", saved+" sont enregistrés");
		map.put("calendars", calendars);
		
		return map;
	}
	
	public Map<String, Object> addSeedCalendarFrequence(CalendarFrequency calendarFrequency) throws NoElementAddException{
	
		Map<String, Object> map = new HashMap<String, Object>();
		int number = (calendarFrequency.getEnd() - calendarFrequency.getStart())/calendarFrequency.getFrequence();
		java.util.Date date = new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		int saved = 0;
		List<SpeculationCalendaryMin> mins = new ArrayList<SpeculationCalendaryMin>();
		for (int i = 0; i <= number; i++) {
			int dayToAdd = (calendarFrequency.getFrequence() * i) + calendarFrequency.getStart() ;
			SpeculationCalendaryMin min = new SpeculationCalendaryMin();
			min.setIntervention(calendarFrequency.getIntervention());
			min.setName(calendarFrequency.getCalendaryName());
			min.setOld(dayToAdd);
			min.setSeed(calendarFrequency.getSeed());
			min.setCreatedOn(sqlStartDate);
			min.setUpdatedOn(sqlStartDate);
			
			SpeculationCalendaryMin savedMin = speculationCalendaryMinRepository.save(min);
			if (savedMin != null) {
				mins.add(savedMin);
				saved++;
			}
			
		}
		
		map.put("success", true);
		map.put("message", saved+" sont enregistrés");
		map.put("calendars", mins);
		
		return map;
	}
	
	public Map<String, Object> addCategoryPoultryMin(CalendarFrequency calendarFrequency)  throws NoElementAddException{
		Map<String, Object> map = new HashMap<String, Object>();
		int number = (calendarFrequency.getEnd() - calendarFrequency.getStart())/calendarFrequency.getFrequence();
		java.util.Date date = new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		int saved = 0;
		List<PoultryCalendaryMin> mins = new ArrayList<PoultryCalendaryMin>();
		for (int i = 0; i <= number; i++) {
			int dayToAdd = (calendarFrequency.getFrequence() * i) + calendarFrequency.getStart() ;
			PoultryCalendaryMin min = new PoultryCalendaryMin();
			min.setIntervention(calendarFrequency.getIntervention());
			min.setName(calendarFrequency.getCalendaryName());
			min.setOld(dayToAdd);
			min.setCategory(calendarFrequency.getPoultryCategory());
			min.setCreatedOn(sqlStartDate);
			min.setUpdatedOn(sqlStartDate);
			
			PoultryCalendaryMin savedMin = poultryCalendaryMinRepository.save(min);
			if (savedMin != null) {
				mins.add(savedMin);
				saved++;
			}
		}
		
		map.put("success", true);
		map.put("message", saved+" sont enregistrés");
		map.put("calendars", mins);
		
		return map;
	}
	
	public Map<String, Object> addCategoryTreeMin(CalendarFrequency calendarFrequency) throws NoElementAddException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int number = (calendarFrequency.getEnd() - calendarFrequency.getStart())/calendarFrequency.getFrequence();
		java.util.Date date = new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		int saved = 0;
		List<TreeCalendaryMin> mins = new ArrayList<TreeCalendaryMin>();
		for (int i = 0; i <= number; i++) {
			int dayToAdd = (calendarFrequency.getFrequence() * i) + calendarFrequency.getStart() ;
			TreeCalendaryMin min = new TreeCalendaryMin();
			min.setIntervention(calendarFrequency.getIntervention());
			min.setName(calendarFrequency.getCalendaryName());
			min.setOld(dayToAdd);
			min.setCategory(calendarFrequency.getTreeCategory());
			min.setCreatedOn(sqlStartDate);
			min.setUpdatedOn(sqlStartDate);
			
			TreeCalendaryMin savedMin = treeCalendaryMinRepository.save(min);
			if (savedMin != null) {
				mins.add(savedMin);
				saved++;
			}
		}
		
		map.put("success", true);
		map.put("message", saved+" sont enregistrés");
		map.put("calendars", mins);
		return map;
	}
	
	public Map<String, Object> addCategoryFishMin(CalendarFrequency calendarFrequency) throws NoElementAddException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int number = (calendarFrequency.getEnd() - calendarFrequency.getStart())/calendarFrequency.getFrequence();
		java.util.Date date = new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		int saved = 0;
		List<FishCalendaryMin> mins = new ArrayList<FishCalendaryMin>();
		for (int i = 0; i <= number; i++) {
			int dayToAdd = (calendarFrequency.getFrequence() * i) + calendarFrequency.getStart() ;
			FishCalendaryMin min = new FishCalendaryMin();
			min.setIntervention(calendarFrequency.getIntervention());
			min.setName(calendarFrequency.getCalendaryName());
			min.setOld(dayToAdd);
			min.setCategory(calendarFrequency.getFishCategory());
			min.setCreatedOn(sqlStartDate);
			min.setUpdatedOn(sqlStartDate);
			
			FishCalendaryMin savedMin = fishCalendaryMinRepository.save(min);
			if (savedMin != null) {
				mins.add(savedMin);
				saved++;
			}
		}
		
		map.put("success", true);
		map.put("message", saved+" sont enregistrés");
		map.put("calendars", mins);
		return map;
	}
	
	public Map<String, Object> addCategoryCattleMin(CalendarFrequency calendarFrequency) throws NoElementAddException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int number = (calendarFrequency.getEnd() - calendarFrequency.getStart())/calendarFrequency.getFrequence();
		java.util.Date date = new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		int saved = 0;
		List<CattleCalendaryMin> mins = new ArrayList<CattleCalendaryMin>();
		for (int i = 0; i <= number; i++) {
			int dayToAdd = (calendarFrequency.getFrequence() * i) + calendarFrequency.getStart() ;
			CattleCalendaryMin min = new CattleCalendaryMin();
			min.setIntervention(calendarFrequency.getIntervention());
			min.setName(calendarFrequency.getCalendaryName());
			min.setOld(dayToAdd);
			min.setCategory(calendarFrequency.getCattleCategory());
			min.setCreatedOn(sqlStartDate);
			min.setUpdatedOn(sqlStartDate);
			
			CattleCalendaryMin savedMin = cattleCalendaryMinRepository.save(min);
			if (savedMin != null) {
				mins.add(savedMin);
				saved++;
			}
		}
		
		map.put("success", true);
		map.put("message", saved+" sont enregistrés");
		map.put("calendars", mins);
		return map;
	}
	
}
