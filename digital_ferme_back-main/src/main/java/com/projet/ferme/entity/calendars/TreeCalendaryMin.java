package com.projet.ferme.entity.calendars;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.category.TreeCategory;

@Entity
@Table(name="tbl_tree_calendary_min")
public class TreeCalendaryMin extends MinCalendary{
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private TreeCategory category;

	public TreeCategory getCategory() {
		return category;
	}

	public void setCategory(TreeCategory category) {
		this.category = category;
	}
	
}
