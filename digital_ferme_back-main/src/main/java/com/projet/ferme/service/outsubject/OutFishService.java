package com.projet.ferme.service.outsubject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.outsubject.OutFish;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.subject.Fish;
import com.projet.ferme.repository.outsubject.OutFishRepository;
import com.projet.ferme.repository.subject.FishRepository;
import com.projet.ferme.service.utile.UserAuthenticate;

@Service
public class OutFishService {

	@Autowired
	private OutFishRepository outRepository;
	@Autowired
	private FishRepository fishRepository;
	@Autowired
	private UserAuthenticate userAuthenticate;
	
	public Map<String, Object> add(OutFish out){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		User user = userAuthenticate.getUserAuthenticate();
		// Check if the fish object is present
		Fish fish = fishRepository.getById(out.getFish().getId());
		if(fish == null || fish.getPresent() == false) {
			returnMap.put("succes", false);
		}else {
			out.setUser(user);
			OutFish outFish = outRepository.save(out);
			if(outFish == null) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué");
			}else {
				returnMap.put("success", true);
				returnMap.put("message", "Enregistré avec succé");
				returnMap.put("outFish", outFish);
			}
		}
		return returnMap ;
	}
}
