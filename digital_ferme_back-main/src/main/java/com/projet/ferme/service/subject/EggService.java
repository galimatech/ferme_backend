package com.projet.ferme.service.subject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.category.PoultryCategory;
import com.projet.ferme.entity.stocks.OutgoingStock;
import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.subject.Egg;
import com.projet.ferme.entity.subject.Poultry;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.category.PoultryCategoryRepository;
import com.projet.ferme.repository.subject.EggRepository;
import com.projet.ferme.service.comptability.SaleService;
import com.projet.ferme.service.outsubject.OutgoingService;

@Service
public class EggService {

	@Autowired
	private EggRepository repository;
	@Autowired
	private OutgoingService outgoingService;
	@Autowired
	private SaleService saleService;
	@Autowired
	private PoultryCategoryRepository categoryRepository;
	
	public Map<String, Object> add(Egg egg) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Poultry poultry = egg.getPoultry();
		PoultryCategory category = categoryRepository.getById(poultry.getCategory().getId());
		System.out.println(category.toString());
		if(category.getType().equals("pulpit")) {
			returnMap.put("success", false);
			returnMap.put("message", "Désolé ces poules ne pondent pas");
		}else if(poultry.getPresent() == false){
			returnMap.put("success", false);
			returnMap.put("message", "Désolé ces poules ont quitté la ferme");
		}else if (egg.getQuantity() > 0) {
			Egg newEgg = repository.save(egg);
			if (newEgg == null) {
				returnMap.put("success", false);
				returnMap.put("message", "Désolé l'enregistrement a échoué");
			}else {
				if(egg.getDestination().equals("stock")) {
					saveStock(null, newEgg);
				}
				if(egg.getDestination().equals("sale")) {
					saveSale(null, newEgg);
				}
				returnMap.put("success", true);
				returnMap.put("egg",newEgg);
				returnMap.put("message", "L'enregistrement a réussit");
			}
		}else{
			returnMap.put("success", false);
			returnMap.put("message", "Veuillez verifier la quantité");
		}
		
		return returnMap;
	}
	
	public Map<String, Object> update(Egg egg) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		Egg newEgg = repository.save(egg);
		if (newEgg == null) {
			returnMap.put("success", false);
			returnMap.put("message", "Désolé l'enregistrement a échoué");
		}else {
			returnMap.put("success", true);
			returnMap.put("egg",newEgg);
			returnMap.put("message", "L'enregistrement a réussit");
		}
		
		return returnMap;
	}
	
	public Map<String, Object> findAll() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<Egg> eggs = repository.findAll();
		returnMap.put("success", true);
		returnMap.put("eggs", eggs);
		
		return returnMap;
	}
	
	public Map<String, Object> findByPoultry(Long id)  throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<Egg> eggs = repository.findByPoultry_id(id);
		
			returnMap.put("success", true);
			returnMap.put("eggs", eggs);
			
			return returnMap;
			
		
		
	}
	
	public Map<String, Object> delete(Long id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Egg egg = repository.getById(id);
		if(egg == null) {
			returnMap.put("success", false);
			returnMap.put("message", "ces oeufs sont deja supprimés");
		}else {
			
			repository.deleteById(id);
			returnMap.put("success", true);
			returnMap.put("message", "Supprimé avec succé");
		}
		return returnMap;
	}
	
	private void saveSale(Long id,Egg egg) {
		String subjectId = "egg_"+egg.getId().toString();
		Sale sale = new Sale();
		sale.setId(id);
		sale.setProduit("Oeuf");
		sale.setPrice(0);
		sale.setQuantity(egg.getQuantity());
		sale.setDate(LocalDateTime.now());
		sale.setCreatedOn(egg.getCreatedOn());
		sale.setUpdatedOn(egg.getUpdatedOn());
		sale.setSubjectId(subjectId);
		saleService.add(sale);
	}
	
	private void saveStock(Long id,Egg egg) {
		String subjectId = "egg_"+egg.getId().toString();
		OutgoingStock out= new OutgoingStock();
		out.setId(id);
		out.setType("in");
		out.setCreatedOn(egg.getCreatedOn());
		out.setUpdatedOn(egg.getUpdatedOn());
		out.setQuantity(egg.getQuantity());
		out.setProduit("Oeuf");
		out.setSubjectId(subjectId);
		outgoingService.add(out);
	}
}
