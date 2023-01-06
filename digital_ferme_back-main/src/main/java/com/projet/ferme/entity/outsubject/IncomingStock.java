package com.projet.ferme.entity.outsubject;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.projet.ferme.entity.stocks.Stock;

@Entity
@Table(name = "tbl_incoming_stock")
public class IncomingStock extends Stock{
	
	private Date date;
	
	private String product;
	
	private String category;
	
	private int value;
	
	private String initValue;
	
	private int volume;
	
	private String unitVolume;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProduct() {
		return product;
	}

	public void setString(String product) {
		this.product = product;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getUnitValue() {
		return initValue;
	}

	public void setUnitValue(String initValue) {
		this.initValue = initValue;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getUnitVolume() {
		return unitVolume;
	}

	public void setUnitVolume(String unitVolume) {
		this.unitVolume = unitVolume;
	}
	
}
