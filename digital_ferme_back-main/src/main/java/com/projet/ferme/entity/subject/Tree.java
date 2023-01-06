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

import com.projet.ferme.entity.calendars.CalendaryTree;
import com.projet.ferme.entity.category.TreeCategory;
import com.projet.ferme.entity.homesubject.Planting;
import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_tree") 
public class Tree extends TimeModel{
	
	private String name;
	
	private boolean present;
	
	@Column(name="date_planting")
	private Date plantingDate;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private TreeCategory category;
	
	@ManyToOne
	@JoinColumn(name="planting_id")
	private Planting planting;
	
	@OneToMany(cascade=CascadeType.ALL ,mappedBy="tree")
	private Set<CalendaryTree> calendary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public Date getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(Date plantingDate) {
		this.plantingDate = plantingDate;
	}

	public TreeCategory getCategory() {
		return category;
	}

	public void setCategory(TreeCategory category) {
		this.category = category;
	}
	
	public String getCategoryName() {
		return category.getName();
	}

	public Planting getPlanting() {
		return planting;
	}

	public void setPlanting(Planting planting) {
		this.planting = planting;
	}
	
	public String getPlantingName() {
		return planting.getName();
	}

	public Set<CalendaryTree> getCalendary() {
		return calendary;
	}

	public void setCalendary(Set<CalendaryTree> calendary) {
		this.calendary = calendary;
	}
	
}
