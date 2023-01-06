package com.projet.ferme.entity.comptability;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_category_compte")
public class CategoryCompte extends TimeModel{
    
    private String name;

    private boolean debitAccount;
	
	@ManyToOne
	@JoinColumn(name="parent_id", nullable = true)
	private CategoryCompte parent;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryCompte getParent() {
        return this.parent;
    }

    public void setParent(CategoryCompte parent) {
        this.parent = parent;
    }

    public boolean isDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(boolean debitAccount) {
        this.debitAccount = debitAccount;
    }
}
