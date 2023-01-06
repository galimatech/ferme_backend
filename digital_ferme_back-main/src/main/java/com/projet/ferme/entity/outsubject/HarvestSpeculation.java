package com.projet.ferme.entity.outsubject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.subject.Speculation;

@Entity
@Table(name="tbl_harvest_speculation")
public class HarvestSpeculation extends Out{
	
	@ManyToOne
	@JoinColumn(name="speculation_id")
	private Speculation speculation;

	public Speculation getSpeculation() {
		return speculation;
	}

	public void setSpeculation(Speculation speculation) {
		this.speculation = speculation;
	}
}
