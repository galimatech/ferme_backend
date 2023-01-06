package com.projet.ferme.entity.homesubject;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.comptability.UseFor;
import com.projet.ferme.entity.subject.Speculation;
import com.projet.ferme.entity.subject.Tree;

@Entity
@Table(name="tbl_planting")
public class Planting extends LocalModel{
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="planting")
	private Set<Speculation> speculation;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="planting")
	private Set<Tree> tree;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planting")
	private Set<UseFor> useFor;

	/*
	 * public Set<Speculation> getSpeculation() { return speculation; }
	 */

	public void setSpeculation(Set<Speculation> speculation) {
		this.speculation = speculation;
	}

	/*
	 * public Set<Tree> getTree() { return tree; }
	 */

	public void setTree(Set<Tree> tree) {
		this.tree = tree;
	}
	
	
	
}
