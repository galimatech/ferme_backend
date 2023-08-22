package com.projet.ferme.entity.homesubject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.subject.Egg;
import com.projet.ferme.entity.subject.Poultry;

@Table(name = "tbl_in_incubator")
@Entity
public class InIncubator extends LocalModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date enterDate;
	private Date exiteDate;
	private int quantity;
	private int alive;
	private int lost;

	@ManyToOne
	@JoinColumn(name = "egg_id")
	private Egg egg;

	@ManyToOne
	@JoinColumn(name = "incubator_id")
	private Incubator incubator;

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public Date getExiteDate() {
		return exiteDate;
	}

	public void setExiteDate(Date exiteDate) {
		this.exiteDate = exiteDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Incubator getIncubator() {
		return incubator;
	}

	public void setIncubator(Incubator incubator) {
		this.incubator = incubator;
	}

	public int getAlive() {
		return alive;
	}

	public void setAlive(int alive) {
		this.alive = alive;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public Egg getEgg() {
		return egg;
	}

	public void setEgg(Egg egg) {
		this.egg = egg;
	}

}
