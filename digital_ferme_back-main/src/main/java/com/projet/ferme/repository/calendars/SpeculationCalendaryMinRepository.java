package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.SpeculationCalendaryMin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeculationCalendaryMinRepository extends JpaRepository<SpeculationCalendaryMin, Long>{

	List<SpeculationCalendaryMin> findBySeedId(Long id);

}
