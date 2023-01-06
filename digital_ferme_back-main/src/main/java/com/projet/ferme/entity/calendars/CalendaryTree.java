package com.projet.ferme.entity.calendars;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.subject.Tree;

@Entity
@Table(name="tbl_calendary_tree")
public class CalendaryTree extends Calendary{
	
	@ManyToOne
	@JoinColumn(name="tree_id")
	private Tree tree;

	public void setTree(Tree tree) {
		this.tree = tree;
	}
	
	
}
