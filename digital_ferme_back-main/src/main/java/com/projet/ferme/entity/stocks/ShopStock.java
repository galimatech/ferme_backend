package com.projet.ferme.entity.stocks;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_shop_stock")
public class ShopStock extends Stock{

	private String product;
	
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;

	public String getProduit() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
