package com.projet.ferme.entity.homesubject;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.comptability.UseFor;
import com.projet.ferme.entity.subject.Fish;

@Entity
@Table(name="tbl_bowl")
public class Bowl extends LocalModel{
	
	private int depth;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="bowl")
	private Set<Fish> fish;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bowl")
	private Set<UseFor> useFor;

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	/*
	 * public Set<Fish> getFish() { return fish; }
	 */

	public void setFish(Set<Fish> fish) {
		this.fish = fish;
	}
	
	

}
