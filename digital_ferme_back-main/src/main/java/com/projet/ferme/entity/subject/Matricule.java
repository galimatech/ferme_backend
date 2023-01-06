package com.projet.ferme.entity.subject;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_matricule")
public class Matricule extends TimeModel{
	
	private String article;

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}
	
}
