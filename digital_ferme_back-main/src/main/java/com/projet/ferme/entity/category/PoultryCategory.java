package com.projet.ferme.entity.category;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.projet.ferme.entity.calendars.PoultryCalendaryMin;
import com.projet.ferme.entity.subject.Poultry;

@Entity
@Table(name="tbl_poultry_category")
public class PoultryCategory extends Category{
	
	private String type;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<Poultry> poultry;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<PoultryCalendaryMin> minCalendary;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPoultry(Set<Poultry> poultry) {
		this.poultry = poultry;
	}

	public void setMinCalendary(Set<PoultryCalendaryMin> minCalendary) {
		this.minCalendary = minCalendary;
	}
	
}
