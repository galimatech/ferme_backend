package com.projet.ferme.entity.subject;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.calendars.CalendaryPoultry;
import com.projet.ferme.entity.category.PoultryCategory;
import com.projet.ferme.entity.homesubject.ChickenCoop;
import com.projet.ferme.entity.outsubject.OutPoultry;
import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_poultry")
public class Poultry extends TimeModel{
	
	private String name;
	
	@Column(name="development_time")
	private Integer developmentTime;
	
	@Column(name="date_of_entry")
	private Date dateOfEntry;
	
	private Integer quantity;
	
	private Integer initQuantity;
	
	private Boolean present;
	
	@ManyToOne
	@JoinColumn(name="chickenCoop_id")
	private ChickenCoop chickenCoop;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private PoultryCategory category;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="poultry")
	private Set<CalendaryPoultry> calendary;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="poultry")
	private Set<OutPoultry> outPoultry;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="poultry")
	private Set<Egg> egg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDevelopmentTime() {
		return developmentTime;
	}

	public void setDevelopmentTime(Integer developmentTime) {
		this.developmentTime = developmentTime;
	}

	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getInitQuantity() {
		return initQuantity;
	}

	public void setInitQuantity(Integer initQuantity) {
		this.initQuantity = initQuantity;
	}

	public Boolean getPresent() {
		return present;
	}

	public void setPresent(Boolean present) {
		this.present = present;
	}

	public ChickenCoop getChickenCoop() {
		return chickenCoop;
	}

	public void setChickenCoop(ChickenCoop chickenCoop) {
		this.chickenCoop = chickenCoop;
	}

	//public Set<CalendaryPoultry> getCalendary() {return calendary;}

	//public void setCalendary(Set<CalendaryPoultry> calendary) {this.calendary = calendary;}

	//public Set<OutPoultry> getOutPoultry() {
		//return outPoultry;
	//}

	public void setOutPoultry(Set<OutPoultry> outPoultry) {
		this.outPoultry = outPoultry;
	}

	public PoultryCategory getCategory() {
		return category;
	}

	public void setCategory(PoultryCategory category) {
		this.category = category;
	}
	
	public String getCategoryName() {
		return category.getName();
	}
	
	public String getCoopsName() {
		return chickenCoop.getName();
	}
	
}
