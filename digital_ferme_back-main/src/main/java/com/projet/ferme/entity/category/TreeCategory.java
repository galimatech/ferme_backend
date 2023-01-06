package com.projet.ferme.entity.category;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.calendars.TreeCalendaryMin;
import com.projet.ferme.entity.subject.Tree;

@Entity
@Table(name="tbl_tree_category")
public class TreeCategory extends Category{
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<Tree> tree;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private Set<TreeCalendaryMin> minCalendary;

	/*
	 * public Set<Tree> getTree() { return tree; }
	 */

	public void setTree(Set<Tree> tree) {
		this.tree = tree;
	}

	/*
	 * public Set<TreeCalendaryMin> getMinCalenday() { return minCalenday; }
	 */

	public void setMinCalendary(Set<TreeCalendaryMin> minCalendary) {
		this.minCalendary = minCalendary;
	}
	
}
