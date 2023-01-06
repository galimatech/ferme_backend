package com.projet.ferme.service.homesubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.homesubject.Planting;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.homesubject.PlantingRepository;

@Service
public class PlantingService {

	@Autowired
	private PlantingRepository repository;
	
	public Map<String, Object> findAllFree(){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Planting> plantings= repository.findByFree(true);
		returnValues.put("success", true);
		returnValues.put("plantings", plantings);
		return returnValues;
	}
	
	public void isNotFree(Planting planting) {
		planting.setFree(false);
		
		repository.save(planting);
	}
	
	public void isFree(Planting planting) {
		planting.setFree(true);
		
		repository.save(planting);
	}
	
	public Map<String, Object> add(Planting P)  {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Planting planting = repository.save(P);
		if(planting == null) {
			returnValues.put("success", false);
			returnValues.put("planting", planting);
		}else {
			returnValues.put("success", true);
			returnValues.put("planting", planting);
		}
		return returnValues;
	}
	
	public Map<String, Object> put(Planting P)  {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Planting planting = repository.save(P);
		if(planting == null) {
			returnValues.put("success", false);
			returnValues.put("planting", planting);
		}else {
			returnValues.put("success", true);
			returnValues.put("planting", planting);
		}
		return returnValues;
	}
	
	public Map<String, Object> findAll() {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Planting> plantings = repository.findAll();
		if(plantings == null) {
			returnValues.put("success", false);
			returnValues.put("plantings", plantings);
		}else {
			returnValues.put("success", true);
			returnValues.put("plantings", plantings);
		}
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id)  throws NoElementFoundException{
		Map<String, Object> returnValues = new HashMap<String, Object>();
		repository.deleteById(id);
		
		returnValues.put("success", true);
			
		return returnValues;
	}
	
	public Planting findById(Long id) {
		return repository.findById(id).get();
	}
}
