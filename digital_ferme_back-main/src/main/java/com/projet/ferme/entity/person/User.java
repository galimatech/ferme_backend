package com.projet.ferme.entity.person;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.projet.ferme.entity.outsubject.HarvestSpeculation;
import com.projet.ferme.entity.outsubject.IncomingStock;
import com.projet.ferme.entity.outsubject.OutCattle;
import com.projet.ferme.entity.outsubject.OutFish;
import com.projet.ferme.entity.outsubject.OutPoultry;
import com.projet.ferme.entity.stocks.OutgoingStock;

@Entity
@Table(name = "tbl_user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	private String firstname;
	
	private String lastname;
	
	private String username;
	
	private String password;
	
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private Set<Cashier> cashiers;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private Set<IncomingStock> incomingStock;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private Set<OutgoingStock> outgoingStock;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private Set<OutCattle> outCattles;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private Set<OutFish> outFishs;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private Set<OutPoultry> outPoultries;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private Set<HarvestSpeculation> harvestSpeculations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * public Role getRole() { return role; }
	 */
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setCashiers(Set<Cashier> cashiers) {
		this.cashiers = cashiers;
	}
	
	
}
