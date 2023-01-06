package com.projet.ferme.service.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.category.TreeCategory;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.category.TreeCategoryRepository;

@Service
public class CategoryTreeService {

	@Autowired
	private TreeCategoryRepository repository;
	
	public Map<String, Object> add(TreeCategory T) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		TreeCategory category = repository.save(T);
		if(category == null) {
			returnMap.put("success",false);
			returnMap.put("category", category);
		}else {
			returnMap.put("success",true);
			returnMap.put("category", category);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> put(TreeCategory T) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		TreeCategory category = repository.save(T);
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
		List<TreeCategory> categorys = repository.findAll();
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
	
	public TreeCategory findById(Long id)  throws NoElementFoundException{
		return repository.findById(id).get();
	}
}
