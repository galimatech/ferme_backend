package com.projet.ferme.entity.subject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.utils.TimeModel;

@Table(name = "tbl_egg")
@Entity()
public class Egg extends TimeModel{
	
	private int quantity;
	
	private String destination;

	@ManyToOne
	@JoinColumn(name = "poultry_id")
	private Poultry poultry;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Poultry getPoultry() {
		return poultry;
	}

	public void setPoultry(Poultry poultry) {
		this.poultry = poultry;
	}
}
