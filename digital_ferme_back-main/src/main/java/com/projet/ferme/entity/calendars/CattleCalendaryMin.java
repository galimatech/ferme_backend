package com.projet.ferme.entity.calendars;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.category.CattleCategory;

@Entity
@Table(name="tbl_cattle_calendar_min")
public class CattleCalendaryMin extends MinCalendary{
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private CattleCategory category;

	public CattleCategory getCategory() {
		return category;
	}

	public void setCategory(CattleCategory category) {
		this.category = category;
	}
	
}
