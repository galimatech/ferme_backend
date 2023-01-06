package com.projet.ferme.entity.category;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.projet.ferme.entity.calendars.SpeculationCalendaryMin;
import com.projet.ferme.entity.subject.Speculation;
import com.projet.ferme.entity.utils.TimeModel;

@Entity
@Table(name="tbl_seed")
public class Seed extends TimeModel{
	
	@Column(name="seed_name")
	private String seedName;
	
	private int transplantingOld;
	private String unity;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="seed")
	private Set<Speculation> speculation;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="seed")
	private Set<SpeculationCalendaryMin> minCalendary;

	public String getSeedName() {
		return seedName;
	}

	public void setSeedName(String seedName) {
		this.seedName = seedName;
	}

	/*
	 * public Set<Speculation> getSpeculation() { return speculation; }
	 */

	public void setSpeculation(Set<Speculation> speculation) {
		this.speculation = speculation;
	}

	/*
	 * public Set<SpeculationCalendaryMin> getMinCalendary() { return minCalendary;
	 * }
	 */

	public void setMinCalendary(Set<SpeculationCalendaryMin> minCalendary) {
		this.minCalendary = minCalendary;
	}

	public int getTransplantingOld() {
		return transplantingOld;
	}

	public void setTransplantingOld(int transplantingOld) {
		this.transplantingOld = transplantingOld;
	}

	public void setunity(String unity){
		this.unity=unity;
	}

	public String getUnity(){
		return this.unity;
	}

}
