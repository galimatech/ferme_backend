package com.projet.ferme.entity.homesubject;

import javax.persistence.MappedSuperclass;

import com.projet.ferme.entity.utils.TimeModel;

@MappedSuperclass
public class LocalModel extends TimeModel{

	private String name;
	
	private Integer area;
	
	private boolean free;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
	

}
