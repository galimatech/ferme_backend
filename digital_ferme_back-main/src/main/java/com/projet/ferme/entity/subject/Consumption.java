package com.projet.ferme.entity.subject;

import java.sql.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Consumption {

	private Date date;
	private int day;
	private int week;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "poultry_id")
	private Poultry poultry;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Poultry getPoultry() {
		return poultry;
	}

	public void setPoultry(Poultry poultry) {
		this.poultry = poultry;
	}
	
}