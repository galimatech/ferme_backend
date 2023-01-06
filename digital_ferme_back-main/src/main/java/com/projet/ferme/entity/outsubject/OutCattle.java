package com.projet.ferme.entity.outsubject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.subject.Cattle;

@Entity
@Table(name="tbl_out_cattle")
public class OutCattle extends Out{
	
	@ManyToOne
	@JoinColumn(name="cattle_id")
	private Cattle cattle;

	public Cattle getCattle() {
		return cattle;
	}

	public void setCattle(Cattle cattle) {
		this.cattle = cattle;
	}
	
}
