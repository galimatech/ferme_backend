package com.projet.ferme.service.subject;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.calendars.CalendaryTree;
import com.projet.ferme.entity.calendars.TreeCalendaryMin;
import com.projet.ferme.entity.category.TreeCategory;
import com.projet.ferme.entity.homesubject.Planting;
import com.projet.ferme.entity.subject.Tree;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.calendars.CalendaryTreeRepository;
import com.projet.ferme.repository.calendars.TreeCalendaryMinRepository;
import com.projet.ferme.repository.subject.TreeRepository;
import com.projet.ferme.service.category.CategoryTreeService;
import com.projet.ferme.service.homesubject.PlantingService;

@Service
public class TreeService {

	@Autowired
	private TreeRepository treeRepository;
	@Autowired
	private TreeCalendaryMinRepository minRepository;
	@Autowired
	private CalendaryTreeRepository calendaryTreeRepository;
	@Autowired
	private PlantingService plantingService;
	@Autowired
	private MatriculeService matricule;
	@Autowired
	private CategoryTreeService categoryTreeService;
	
	public Map<String, Object> add(Tree t) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		t.setName(matricule.getName(t.getCategory().getName()));
		Tree tree ;
		tree = treeRepository.save(t);
		if(tree==null) {
			returnValues.put("success", false);
			returnValues.put("tree", tree);
		}else {
			java.util.Date date=new java.util.Date();
			Date sqlStartDate = new Date(date.getTime());
			List<TreeCalendaryMin> mins = minRepository.findByCategoryId(tree.getCategory().getId());
			for(TreeCalendaryMin min: mins) {
				CalendaryTree cal = new  CalendaryTree();
				Calendar c = Calendar.getInstance();
				c.setTime(tree.getPlantingDate());
				c.add(Calendar.DATE,min.getOld());
				cal.setTree(tree);
				cal.setCalendaryName(min.getName());
				Date x = new Date(c.getTimeInMillis());
				cal.setDate(x);
				cal.setCreatedOn(sqlStartDate);
				cal.setIntervention(min.getIntervention());
				cal.setMake(false);
				cal.setGiveUp(false);
				cal.setUpdatedOn(null);
				cal.setId(null);
				
				calendaryTreeRepository.save(cal);
				
				plantingService.isNotFree(tree.getPlanting());
				
			}
			returnValues.put("success", true);
			returnValues.put("tree", tree);
		}
		return returnValues;
	}
	
	public Map<String, Object> put(Tree t) {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Tree tree ;
		tree = treeRepository.save(t);
		if(tree==null) {
			returnValues.put("success", false);
			returnValues.put("tree", tree);
		}else {
			returnValues.put("success", true);
			returnValues.put("tree", tree);
		}
		return returnValues;
	}
	
	public Map<String, Object> findAll(){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Tree> trees = treeRepository.findAll() ;
		
			returnValues.put("success", true);
			returnValues.put("trees", trees);
			
		return returnValues;
	}
	
	public Map<String, Object> findByName(String name){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Optional<Tree> tree = treeRepository.findByName(name) ;
		
		if(tree == null) {
			returnValues.put("success", false);
			returnValues.put("tree", tree);
		}else {
			returnValues.put("success", true);
			returnValues.put("tree", tree);
		}
			
		return returnValues;
	}
	
	public Map<String, Object> findByPresent(boolean b){
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Tree> trees = treeRepository.findByPresent(b) ;
		
			returnValues.put("success", true);
			returnValues.put("trees", trees);
			
			return returnValues;
	}
	
	public Map<String, Object> findByPlantings(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Tree> trees = treeRepository.findByPlanting_id(id);
		Planting planting = plantingService.findById(id);
		
		map.put("trees", trees);
		map.put("planting", planting);
		map.put("success", true);
			
	
		return map;
	}
			
	public Map<String, Object> findByCategory(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Tree> trees = treeRepository.findByCategory_id(id);
		TreeCategory category = categoryTreeService.findById(id);

		map.put("trees", trees);
		map.put("category", category);
		map.put("success", true);
		return map;
	}		
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		treeRepository.deleteById(id);
		returnValues.put("success", true);
		return returnValues;
	}
	
	public Map<String, Object> count() {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		
		int isPresent = treeRepository.countByPresent(true);
		int isMissing = treeRepository.countByPresent(false);
		
		returnValues.put("success", true);
		returnValues.put("present", isPresent);
		returnValues.put("missing",isMissing);
		
		return returnValues;
	}
}
