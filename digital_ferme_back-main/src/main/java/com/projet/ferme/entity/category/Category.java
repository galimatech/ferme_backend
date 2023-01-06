package com.projet.ferme.entity.category;

import javax.persistence.MappedSuperclass;

import com.projet.ferme.entity.utils.TimeModel;

@MappedSuperclass
public class Category extends TimeModel{
	
	private String name;

	private String unity;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnity(){
		return this.unity;
	}

	public void setUnity(String unity){
		this.unity=unity;
	}

}
