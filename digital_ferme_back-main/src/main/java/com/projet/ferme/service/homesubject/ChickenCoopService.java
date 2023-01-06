package com.projet.ferme.service.homesubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.homesubject.ChickenCoop;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.homesubject.ChikenCoopRepository;

@Service
public class ChickenCoopService {

	@Autowired
	ChikenCoopRepository chickenCoopRepository;
	
	public Map<String, Object> add(ChickenCoop chickenCoop) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		ChickenCoop newChickenCoop = chickenCoopRepository.save(chickenCoop);
		
		if(newChickenCoop == null){
			returnValues.put("success", false);
			returnValues.put("chickenCoop", newChickenCoop);
		}else {
			returnValues.put("success", true);
			returnValues.put("chickenCoop", newChickenCoop);
		}
		
		return returnValues;
	}
	
	
	public Map<String, Object> findAll(){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<ChickenCoop> chickenCoops = chickenCoopRepository.findAll();
		
		returnValues.put("success", true);
		returnValues.put("chickenCoops", chickenCoops);
		
		return returnValues;
	}
	
	
	public Map<String, Object> findAllFree(){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<ChickenCoop> chickenCoops = chickenCoopRepository.findByFree(true);
		
		returnValues.put("success", true);
		returnValues.put("chickenCoops", chickenCoops);
		
		return returnValues;
	}
	
	public Map<String, Object> put(ChickenCoop chickenCoop) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		ChickenCoop newChickenCoop = chickenCoopRepository.save(chickenCoop);
		
		if(newChickenCoop != null) {
			returnValues.put("success", true);
			returnValues.put("chickenCoop", newChickenCoop);
		}else {
			returnValues.put("success", false);
			returnValues.put("chickenCoop", newChickenCoop);
		}
		
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		
		chickenCoopRepository.deleteById(id);
		returnValues.put("success", true);
		
		return returnValues;
	}
}
