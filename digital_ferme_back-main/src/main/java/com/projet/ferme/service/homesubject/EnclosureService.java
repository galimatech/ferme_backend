package com.projet.ferme.service.homesubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.homesubject.Enclosure;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.homesubject.EnclosureRepository;

@Service
public class EnclosureService {

	@Autowired
	EnclosureRepository enclosureRepository;
	
	public Map<String, Object> add(Enclosure enclosure) {
			
			Map<String, Object> returnValues = new HashMap<String, Object>();
			Enclosure newEnclosure = enclosureRepository.save(enclosure);
			
			if(newEnclosure == null){
				returnValues.put("success", false);
				returnValues.put("enclosure", newEnclosure);
			}else {
				returnValues.put("success", true);
				returnValues.put("enclosure", newEnclosure);
			}
			
			return returnValues;
	}
	
	public Map<String, Object> findAll() {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<Enclosure> enclosures = enclosureRepository.findAll();
		
		returnValues.put("success", true);
		returnValues.put("enclosures", enclosures);
		
		return returnValues;
	}
	
	public Map<String, Object> put(Enclosure enclosure) throws NoElementFoundException {
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Enclosure newEnclosure = enclosureRepository.save(enclosure);
		
		returnValues.put("success", true);
		returnValues.put("enclosure", newEnclosure);
		
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id)  throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		enclosureRepository.deleteById(id);
		returnValues.put("success", true);
		
		return returnValues;
	}
}
