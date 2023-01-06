package com.projet.ferme.service.subject;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendaryCattle;
import com.projet.ferme.entity.calendars.CattleCalendaryMin;
import com.projet.ferme.entity.category.CattleCategory;
import com.projet.ferme.entity.homesubject.Enclosure;
import com.projet.ferme.entity.subject.Cattle;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendaryCattleRepository;
import com.projet.ferme.repository.calendars.CattleCalendaryMinRepository;
import com.projet.ferme.repository.homesubject.EnclosureRepository;
import com.projet.ferme.repository.subject.CattleRepository;
import com.projet.ferme.service.category.CategoryCattleService;

@Service
public class CattleService {

	@Autowired
	private CattleRepository cattleRepository;
	@Autowired
	private CalendaryCattleRepository calendaryCattleRepository;
	@Autowired
	private CattleCalendaryMinRepository minRepository;
	@Autowired
	private MatriculeService matricule;
	@Autowired
	private EnclosureRepository enclosureRepository;
	@Autowired
	private CategoryCattleService categoryCattleService;

	public Map<String, Object> add(Cattle cattle) {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		if (cattle.getEnclosure() == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Veuillez choisir un enclos");
		} else if (cattle.getCategory() == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Veuillez choisir une categorie");
		} else {
			cattle.setName(matricule.getName(cattle.getCategory().getName()));
			Cattle newValue = cattleRepository.save(cattle);

			if (newValue == null) {
				returnValues.put("success", false);
				returnValues.put("cattle", newValue);
			} else {
				java.util.Date date = new java.util.Date();
				Date sqlStartDate = new Date(date.getTime());
				List<CattleCalendaryMin> mins = minRepository.findByCategoryId(newValue.getCategory().getId());
				for (CattleCalendaryMin min : mins) {
					CalendaryCattle cal = new CalendaryCattle();
					Calendar c = Calendar.getInstance();
					c.setTime(newValue.getDate());
					c.add(Calendar.DATE, min.getOld());
					cal.setCattle(newValue);
					cal.setCalendaryName(min.getName());
					Date x = new Date(c.getTimeInMillis());
					cal.setDate(x);
					cal.setCreatedOn(sqlStartDate);
					cal.setIntervention(min.getIntervention());
					cal.setMake(false);
					cal.setGiveUp(false);
					cal.setUpdatedOn(null);
					cal.setId(null);

					calendaryCattleRepository.save(cal);

				}
				returnValues.put("success", true);
				returnValues.put("cattle", newValue);
			}
		}

		return returnValues;
	}

	public Map<String, Object> findAll() {

		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Cattle> cattles = cattleRepository.findAll();

		returnValues.put("success", true);
		returnValues.put("cattles", cattles);

		return returnValues;
	}

	public Map<String, Object> cattleByEnclosure(Long id)  throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Cattle> cattles = cattleRepository.findByEnclosure_id(id);
		Optional<Enclosure> enclosure = enclosureRepository.findById(id);

		map.put("success", true);
		map.put("enclosure", enclosure.get());
		map.put("cattles", cattles);

		return map;
	}

	public Map<String, Object> cattleByCategory(Long id) throws NoElementFoundException {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Cattle> cattles = cattleRepository.findByCategory_id(id);
		CattleCategory category = categoryCattleService.findById(id);
		map.put("success", true);
		map.put("cattles", cattles);
		map.put("category", category);

		return map;
	}

	public Map<String, Object> findByPresent(boolean b) {

		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Cattle> cattles = cattleRepository.findByPresent(b);

		returnValues.put("success", true);
		returnValues.put("cattles", cattles);

		return returnValues;
	}

	public Map<String, Object> findByName(String name) {

		Map<String, Object> returnValues = new HashMap<String, Object>();
		Optional<Cattle> cat = cattleRepository.findByName(name);

		if (cat == null) {
			returnValues.put("success", false);
			returnValues.put("cattle", cat);
		} else {
			returnValues.put("success", true);
			returnValues.put("cattle", cat);
		}

		return returnValues;
	}

	public Map<String, Object> put(Cattle cattle)  throws NoElementFoundException{

		Map<String, Object> returnValues = new HashMap<String, Object>();
		Cattle newValue = cattleRepository.save(cattle);

		returnValues.put("success", true);
		returnValues.put("cattle", newValue);

		return returnValues;
	}

	public Map<String, Object> delete(Long id) throws NoElementFoundException{

		Map<String, Object> returnValues = new HashMap<String, Object>();
		cattleRepository.deleteById(id);
		returnValues.put("success", true);

		return returnValues;
	}

	public Map<String, Object> count() {
		Map<String, Object> returnValues = new HashMap<String, Object>();

		int isPresent = cattleRepository.countByPresent(true);
		int isMissing = cattleRepository.countByPresent(false);

		returnValues.put("success", true);
		returnValues.put("present", isPresent);
		returnValues.put("missing", isMissing);

		return returnValues;
	}
}
