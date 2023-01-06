package com.projet.ferme.entity.person;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.comptability.Reimburse;
import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.entity.utils.TimeModel;

@Table(name = "tbl_cashier")
@Entity
public class Cashier extends TimeModel{

	private boolean active;
	
	private int access;
	
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name =  "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name =  "shop_id")
	private Shop shop;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cashier")
	private Set<Sale> sales;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cashier")
	private Set<Reimburse> reimburses;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getAccess() {
		return access;
	}

	public void setAcess(int access) {
		this.access = access;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	
}
