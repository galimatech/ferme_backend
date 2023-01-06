package com.projet.ferme.entity.stocks;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.utils.TimeModel;

@MappedSuperclass
public class Stock extends TimeModel{
	
	private Integer quantity;
	
	private String type;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
