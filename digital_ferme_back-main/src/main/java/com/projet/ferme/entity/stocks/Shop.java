package com.projet.ferme.entity.stocks;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.utils.TimeModel;

@Table(name =  "tbl_shop")
@Entity
public class Shop extends TimeModel{

	private String name;
	
	private String adress;
	
	private  String telephone;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="shop")
	private Set<Cashier> cashiers;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shop")
	private Set<ShopStock> shopStocks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
