package com.projet.ferme.entity.calendars;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.category.PoultryCategory;

@Entity
@Table(name="tbl_poultry_calendar_min")
public class PoultryCalendaryMin extends MinCalendary{
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private PoultryCategory category;

	public PoultryCategory getCategory() {
		return category;
	}

	public void setCategory(PoultryCategory category) {
		this.category = category;
	}
	
}
