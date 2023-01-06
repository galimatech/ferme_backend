package com.projet.ferme.entity.comptability;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_compte")
public class Compte extends TimeModel{
	
    @Column(unique = true)
	private String number;
	
	private String name;

	private String description;
	
	@ManyToOne
	@JoinColumn(name="parent_id", nullable = true)
	private Compte parent;	

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Compte getParent() {
        return this.parent;
    }

    public void setParent(Compte parent) {
        this.parent = parent;
    }
    
}
