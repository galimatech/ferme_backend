package com.projet.ferme.entity.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_product") 
public class Product extends TimeModel{
	
	@Column(name="product_name")
	private String productName;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
