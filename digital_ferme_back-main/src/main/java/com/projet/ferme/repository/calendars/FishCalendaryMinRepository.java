package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.FishCalendaryMin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FishCalendaryMinRepository extends JpaRepository<FishCalendaryMin,Long>{

	List<FishCalendaryMin> findByCategoryId(Long id);
}
