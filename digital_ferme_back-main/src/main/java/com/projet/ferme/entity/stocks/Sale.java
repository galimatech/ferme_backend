package com.projet.ferme.entity.stocks;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.projet.ferme.entity.comptability.Reimburse;
import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.person.Customer;
import com.projet.ferme.entity.utils.TimeModel;



@Entity
@Table(name = "tbl_sale")
public class Sale extends TimeModel{
	
	private Integer price;
	
	private Integer advance;
	
	private Integer account;
	
	private Integer quantity;
	
	private String produit;
	
	private LocalDateTime date;
	
	private String description;

	@Column(nullable = false)
	private boolean reimburse = false;
	
	@Column(nullable = false)
	private boolean counted = false;

	@Column(name = "subject_id")
	private String subjectId;
	
	@ManyToOne
	@JoinColumn(name = "cashier_id")
	private Cashier cashier;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToOne(optional=true, mappedBy="sale")
	private Reimburse reimburseEntity;
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getAdvance() {
		return advance;
	}

	public void setAdvance(Integer advance) {
		this.advance = advance;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCounted() {
		return counted;
	}

	public void setCounted(boolean counted) {
		this.counted = counted;
	}

	public boolean isReimburse() {
		return reimburse;
	}

	public void setReimburse(boolean reimburse) {
		this.reimburse = reimburse;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
