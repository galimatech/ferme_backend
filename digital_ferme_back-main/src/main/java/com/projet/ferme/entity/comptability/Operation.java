package com.projet.ferme.entity.comptability;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name = "tbl_operation")
public class Operation extends TimeModel{
    
    private String label;

    private String comment;

    private int amount;

    private LocalDateTime date;

    @OneToOne(optional=true, mappedBy="operation")
    private UseFor useFor;

    @ManyToOne
    @JoinColumn(name = "compte_id", nullable = false)
    private Compte compte;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryCompte category;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public CategoryCompte getCategory() {
        return category;
    }

    public void setCategory(CategoryCompte category) {
        this.category = category;
    }

    public void setUseFor(UseFor useFor) {
        this.useFor = useFor;
    }
}
