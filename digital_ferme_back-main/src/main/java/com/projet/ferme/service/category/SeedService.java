package com.projet.ferme.service.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.projet.ferme.entity.category.Seed;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.category.SeedRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeedService {

	@Autowired
	private SeedRepository repository;
	
	public Map<String, Object> add(Seed S) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Seed seed = repository.save(S);
		if(seed == null) {
			returnMap.put("success",false);
			returnMap.put("seed", seed);
		}else {
			returnMap.put("success",true);
			returnMap.put("seed", seed);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> put(Seed S) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Seed seed = repository.save(S);
		if(seed == null) {
			returnMap.put("success",false);
			returnMap.put("seed", seed);
		}else {
			returnMap.put("success",true);
			returnMap.put("seed", seed);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> findAll(){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Seed> seeds = repository.findAll();
		if(seeds == null) {
			returnMap.put("success",false);
			returnMap.put("seeds", seeds);
		}else {
			returnMap.put("success",true);
			returnMap.put("seeds", seeds);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		repository.deleteById(id);
		
			returnMap.put("success",true);
		
		return returnMap;
	}
	
	public Seed findById(Long id) throws NoElementFoundException{
		return repository.findById(id).get();
	}
}
