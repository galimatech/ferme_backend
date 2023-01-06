package com.projet.ferme.entity.person;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name = "tbl_customer")
public class Customer extends TimeModel{
    
    private String name;

    @Column(nullable = false, unique = true)
    private int telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Sale> sales;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getForSearch(){
        String forSearch = this.name+" "+this.telephone;
        return forSearch;
    }
}
