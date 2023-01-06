package com.projet.ferme.entity.outsubject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.subject.Poultry;

@Entity
@Table(name="tbl_out_poultry")
public class OutPoultry extends Out{
	
	@ManyToOne
	@JoinColumn(name="poultry_id")
	private Poultry poultry;

	public Poultry getPoultry() {
		return poultry;
	}

	public void setPoultry(Poultry poultry) {
		this.poultry = poultry;
	}
	
	
	
}
