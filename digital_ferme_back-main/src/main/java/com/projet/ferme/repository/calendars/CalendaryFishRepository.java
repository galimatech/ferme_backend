package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.CalendaryFish;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendaryFishRepository extends JpaRepository<CalendaryFish, Long>{

	List<CalendaryFish> findByFish_id(Long id);

}
