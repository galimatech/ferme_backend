package com.projet.ferme.entity.stocks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_outgoing_stock")
public class OutgoingStock extends Stock{
	
	private String produit;
	
	@Column(name = "comes_from")
	private String comesFrom;
	
	@Column(name = "subject_id")
	private String subjectId;

	public String getProduit() {
		return produit;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public String getComesFrom() {
		return comesFrom;
	}

	public void setComesFrom(String comesFrom) {
		this.comesFrom = comesFrom;
	}

}
