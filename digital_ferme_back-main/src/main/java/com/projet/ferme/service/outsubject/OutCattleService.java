package com.projet.ferme.service.outsubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.outsubject.OutCattle;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.subject.Cattle;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.outsubject.OutCattleRepository;
import com.projet.ferme.repository.subject.CattleRepository;
import com.projet.ferme.service.utile.UserAuthenticate;

@Service
public class OutCattleService {

	@Autowired
	private OutCattleRepository outCattleRepository;
	@Autowired
	private CattleRepository cattleRepository;
	@Autowired
	private UserAuthenticate userAuthenticate;
	
	public Map<String, Object> add(OutCattle outCattle) {
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Cattle cattle = cattleRepository.getById(outCattle.getCattle().getId());
		// Check if the cattle object is present
		if(cattle == null || cattle.getPresent() == false) {
			returnValues.put("success", false);
			returnValues.put("message", "Ce sujet n'est plus present");
		}else {
			outCattle.setUser(user);
			OutCattle newOutCattle = outCattleRepository.save(outCattle);
			// Check if the out object is saved
			if(newOutCattle == null) {
				returnValues.put("success", false);
				returnValues.put("message", "l'insertion a échoué");
				returnValues.put("outCattle", newOutCattle);
			}else {
				// Update cattle object
				cattle.setPresent(false);
				cattle = cattleRepository.save(cattle);
				returnValues.put("success", true);
				returnValues.put("outCattle", newOutCattle);
			}
		}
		
		return returnValues;
	}
	
	public Map<String, Object> findByCattleId(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<OutCattle> newOutCattles = outCattleRepository.getByCattle_id(id) ;
		
		returnValues.put("success", true);
		returnValues.put("outCattles", newOutCattles);
		
		return returnValues;
	}
	
	public Map<String, Object> put(OutCattle out) {
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		OutCattle oldOut = outCattleRepository.getById(out.getId());
		
		if(oldOut == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Cet enregistrement n'existe plus");
		}else {
			out.setUser(user);
			OutCattle newOutCattle = outCattleRepository.save(out);
			if(newOutCattle == null) {
				returnValues.put("success", false);
				returnValues.put("message", "Echec de la mise a jour");
			}else {
				returnValues.put("success", true);
				returnValues.put("outCattle", newOutCattle);
				returnValues.put("message", "Enregistrement réussit");
			}
		}
		
		return returnValues;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		
		Map<String, Object> returnValues = new HashMap<String, Object>();
		outCattleRepository.deleteById(id);
		returnValues.put("success", true);
		returnValues.put("message", "Supprimé avec succé");
		
		return returnValues;
	}
}
