package com.projet.ferme.entity.category;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.calendars.FishCalendaryMin;
import com.projet.ferme.entity.subject.Fish;

@Entity
@Table(name="tbl_fish_category")
public class FishCategory extends Category{
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<Fish> fish;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<FishCalendaryMin> minCalendary;

	/*
	 * public Set<Fish> getFish() { return fish; }
	 */

	public void setFish(Set<Fish> fish) {
		this.fish = fish;
	}

	/*
	 * public Set<FishCalendaryMin> getMinCalendary() { return minCalendary; }
	 */

	public void setMinCalendary(Set<FishCalendaryMin> minCalendary) {
		this.minCalendary = minCalendary;
	}
	
}
