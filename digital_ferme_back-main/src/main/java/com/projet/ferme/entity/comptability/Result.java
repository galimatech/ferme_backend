package com.projet.ferme.entity.comptability;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.expression.spel.support.DataBindingPropertyAccessor;

/* import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table; */

public class Result {

    private Long id;
    private Compte parent;
    private Compte compte;

    private Long amount;
    private int year ;



 


    public Result(Long id, Compte compte,Long amount, Compte parent, int year){
        this.id=id;
        this.compte = compte;
        this.amount = amount;
        this.parent = parent;
        this.year=year;

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Compte getParent(){
        return compte.getParent();
    }

    public void setParent(Compte parent) {
        this.parent = parent;
    }

    
}
