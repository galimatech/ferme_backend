package com.projet.ferme.entity.homesubject;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "tbl_incubator")
@Entity
public class Incubator extends LocalModel {
	
	private String name;
	
	private int capacity;
	
	public Set<InIncubator> getInIncubator() {
		return inIncubator;
	}

	public void setInIncubator(Set<InIncubator> inIncubator) {
		this.inIncubator = inIncubator;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "egg")
	private Set<InIncubator> inIncubator;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
