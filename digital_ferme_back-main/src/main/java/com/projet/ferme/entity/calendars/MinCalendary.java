package com.projet.ferme.entity.calendars;

import javax.persistence.MappedSuperclass;

import com.projet.ferme.entity.utils.TimeModel;

@MappedSuperclass
public class MinCalendary extends TimeModel{
	
	private String name;
	
	private String intervention;
	
	private int old;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntervention() {
		return intervention;
	}

	public void setIntervention(String intervention) {
		this.intervention = intervention;
	}

	public int getOld() {
		return old;
	}

	public void setOld(int old) {
		this.old = old;
	}

}
