package com.projet.ferme.entity.outsubject;

import java.sql.Date;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.utils.TimeModel;

@MappedSuperclass
public class Out extends TimeModel{
	
	private Date date;
	
	private Integer quantity;
	
	private Integer valeur;
	//private String unitHarvest;


	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/* public String getUnitHarvest(){
		return this.unitHarvest;
	}

	public void setUnitHarvest(String unitHarvest){
		this.unitHarvest=unitHarvest;
	} */
}
