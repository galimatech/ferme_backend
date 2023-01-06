package com.projet.ferme.entity.calendars;

import com.projet.ferme.entity.category.CattleCategory;
import com.projet.ferme.entity.category.FishCategory;
import com.projet.ferme.entity.category.PoultryCategory;
import com.projet.ferme.entity.category.Seed;
import com.projet.ferme.entity.category.TreeCategory;
import com.projet.ferme.entity.subject.Fish;
import com.projet.ferme.entity.subject.Poultry;
import com.projet.ferme.entity.subject.Speculation;

public class CalendarFrequency {

	private Long id;
	
	private String calendaryName;
	
	private String intervention;
	
	private int start;
	
	private int frequence;
	
	private int end;
	
	private Fish fish;
	
	private Poultry poultry;
	
	private Speculation speculation;
	
	private CattleCategory cattleCategory;
	
	private PoultryCategory poultryCategory;
	
	private FishCategory fishCategory;
	
	private Seed seed;
	
	private TreeCategory treeCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalendaryName() {
		return calendaryName;
	}

	public void setCalendaryName(String calendaryName) {
		this.calendaryName = calendaryName;
	}

	public String getIntervention() {
		return intervention;
	}

	public void setIntervention(String intervention) {
		this.intervention = intervention;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public Fish getFish() {
		return fish;
	}

	public void setFish(Fish fish) {
		this.fish = fish;
	}

	public Speculation getSpeculation() {
		return speculation;
	}

	public void setSpeculation(Speculation speculation) {
		this.speculation = speculation;
	}

	public Poultry getPoultry() {
		return poultry;
	}

	public void setPoultry(Poultry poultry) {
		this.poultry = poultry;
	}

	public CattleCategory getCattleCategory() {
		return cattleCategory;
	}

	public void setCattleCategory(CattleCategory cattleCategory) {
		this.cattleCategory = cattleCategory;
	}

	public PoultryCategory getPoultryCategory() {
		return poultryCategory;
	}

	public void setPoultryCategory(PoultryCategory poultryCategory) {
		this.poultryCategory = poultryCategory;
	}

	public FishCategory getFishCategory() {
		return fishCategory;
	}

	public void setFishCategory(FishCategory fishCategory) {
		this.fishCategory = fishCategory;
	}

	public Seed getSeed() {
		return seed;
	}

	public void setSeed(Seed seed) {
		this.seed = seed;
	}

	public TreeCategory getTreeCategory() {
		return treeCategory;
	}

	public void setTreeCategory(TreeCategory treeCategory) {
		this.treeCategory = treeCategory;
	}
	
}
