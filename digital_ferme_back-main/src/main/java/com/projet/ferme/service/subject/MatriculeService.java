package com.projet.ferme.service.subject;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.subject.Matricule;
import com.projet.ferme.repository.subject.MatriculeRepository;

@Service
public class MatriculeService {

	@Autowired
	private MatriculeRepository repository;
	
	public String getName(String name) {

		java.util.Date date=new java.util.Date();
		Date sqlStartDate = new Date(date.getTime());
		
		Matricule mat = new Matricule();
		
		mat.setArticle(name);
		mat.setCreatedOn(sqlStartDate);
		mat.setUpdatedOn(null);
		
		Matricule matricule = repository.save(mat);
		
		String returnValue = "";
		String id = matricule.getId().toString();
		
		if(id.length() == 1){
			returnValue = "_000000";
			returnValue = returnValue.concat(id);
		}else if(id.length() == 2){
			returnValue = "_00000";
			returnValue = returnValue.concat(id);
		}else if(id.length() == 3){
			returnValue = "_0000";
			returnValue = returnValue.concat(id);
		}else if(id.length() == 4){
			returnValue = "_000";
			returnValue.concat(id);
		}else if(id.length() == 5){
			returnValue = "_00";
			returnValue = returnValue.concat(id);
		}else if(id.length() == 6){
			returnValue = "_0";
			returnValue = returnValue.concat(id);
		}else {
			returnValue = "_";
			returnValue = returnValue.concat(id);
		}
		return name.concat(returnValue);
	}
}
