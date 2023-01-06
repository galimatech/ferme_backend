package com.projet.ferme.entity.comptability;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.utils.TimeModel;

@Table(name = "tbl_reimburse")
@Entity
public class Reimburse extends TimeModel{
   
    private boolean counted;

    private LocalDateTime date;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "cashier_id", nullable = false, updatable = false)
    private Cashier cashier;

    @OneToOne(optional = false)
    @JoinColumn(name="sale_id", unique=true, nullable=false, updatable=false)
    private Sale sale;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public boolean isCounted() {
        return counted;
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
    }
}
