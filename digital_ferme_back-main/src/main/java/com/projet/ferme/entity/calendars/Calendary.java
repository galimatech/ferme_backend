package com.projet.ferme.entity.calendars;

import java.sql.Date;

import javax.persistence.MappedSuperclass;

import com.projet.ferme.entity.utils.TimeModel;

@MappedSuperclass
public class Calendary extends TimeModel{

	private String calendaryName;
	
	private String intervention;
	
	private Boolean make = false;
	
	private Boolean giveUp = false;
	
	private String description;
	
	private Date date;

	public String getCalendaryName() {
		return calendaryName;
	}

	public void setCalendaryName(String calendaryName) {
		this.calendaryName = calendaryName;
	}

	public String getIntervention() {
		return intervention;
	}

	public void setIntervention(String intervention) {
		this.intervention = intervention;
	}

	public Boolean getMake() {
		return make;
	}

	public void setMake(Boolean make) {
		this.make = make;
	}

	public Boolean getGiveUp() {
		return giveUp;
	}

	public void setGiveUp(Boolean giveUp) {
		this.giveUp = giveUp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
