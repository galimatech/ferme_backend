package com.projet.ferme.entity.subject;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.calendars.CalendaryFish;
import com.projet.ferme.entity.category.FishCategory;
import com.projet.ferme.entity.homesubject.Bowl;
import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_fish")
public class Fish extends TimeModel{
	
	private String name;
	
	private int quantity;
	
	private Boolean present;
	
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="bowl_id",referencedColumnName="id")
	private Bowl bowl;
	
	@ManyToOne
	@JoinColumn(name="category_id",referencedColumnName="id")
	private FishCategory category;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="fish")
	private Set<CalendaryFish> calendary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Boolean getPresent() {
		return present;
	}

	public void setPresent(Boolean present) {
		this.present = present;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<CalendaryFish> getCalendary() {
		return calendary;
	}

	public void setCalendary(Set<CalendaryFish> calendary) {
		this.calendary = calendary;
	}

	public Bowl getBowl() {
		return bowl;
	}

	public void setBowl(Bowl bowl) {
		this.bowl = bowl;
	}

	public FishCategory getCategory() {
		return category;
	} 

	public void setCategory(FishCategory category) {
		this.category = category;
	}
	
	public String getBowlName() { return bowl.getName(); }
	
	public String getCategoryName() { return getCategory().getName(); }
	
}
