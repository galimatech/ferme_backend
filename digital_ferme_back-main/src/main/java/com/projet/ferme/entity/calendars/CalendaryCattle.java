package com.projet.ferme.entity.calendars;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.subject.Cattle;

@Entity
@Table(name="tbl_calendary_cattle")
public class CalendaryCattle extends Calendary{
	
	@ManyToOne
	@JoinColumn(name="cattle_id")
	private Cattle cattle;

	/*
	 * public Cattle getCattle() { return cattle; }
	 */

	public void setCattle(Cattle cattle) {
		this.cattle = cattle;
	}
	
	
}
