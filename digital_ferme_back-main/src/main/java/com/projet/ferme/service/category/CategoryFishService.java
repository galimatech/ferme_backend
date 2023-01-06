package com.projet.ferme.service.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.category.FishCategory;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.category.FishCategoryRepository;

@Service
public class CategoryFishService {

	@Autowired
	private FishCategoryRepository repository;
	
	public Map<String, Object> add(FishCategory F) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		FishCategory category = repository.save(F);
		if(category == null) {
			returnMap.put("success",false);
			returnMap.put("category", category);
		}else {
			returnMap.put("success",true);
			returnMap.put("category", category);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> put(FishCategory F) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		FishCategory category = repository.save(F);
		if(category == null) {
			returnMap.put("success",false);
			returnMap.put("category", category);
		}else {
			returnMap.put("success",true);
			returnMap.put("category", category);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> findAll(){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<FishCategory> categorys = repository.findAll();
		if(categorys == null) {
			returnMap.put("success",false);
			returnMap.put("categorys", categorys);
		}else {
			returnMap.put("success",true);
			returnMap.put("categorys", categorys);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		repository.deleteById(id);
		
		returnMap.put("success",true);
		
		return returnMap;
	}
	
	public FishCategory findById(Long id) {
		return repository.findById(id).get();
	}
}
