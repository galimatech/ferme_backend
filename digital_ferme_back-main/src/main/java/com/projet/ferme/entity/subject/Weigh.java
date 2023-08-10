package com.projet.ferme.entity.subject;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name = "tbl_weigh")
public class Weigh extends TimeModel {
	
	private Date date;
	private int min;
	private int max;
	private int quantity;
	private int old;
	
	@ManyToOne
	@JoinColumn(name = "poultry_id")
	private Poultry poultry;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOld() {
		return old;
	}

	public void setOld(int old) {
		this.old = old;
	}

}
