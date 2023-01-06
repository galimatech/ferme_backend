package com.projet.ferme.entity.category;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.calendars.CattleCalendaryMin;
import com.projet.ferme.entity.subject.Cattle;

@Entity
@Table(name="tbl_cattle_category")
public class CattleCategory extends Category{
	
	private String family;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<Cattle> cattle;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<CattleCalendaryMin> minCalendary;
	
	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	/*
	 * public Set<Cattle> getCattle() { return cattle; }
	 */

	public void setCattle(Set<Cattle> cattle) {
		this.cattle = cattle;
	}

	/*
	 * public Set<CattleCalendaryMin> getMinCalendary() { return minCalendary; }
	 */

	public void setMinCalendary(Set<CattleCalendaryMin> minCalendary) {
		this.minCalendary = minCalendary;
	}
	
}
