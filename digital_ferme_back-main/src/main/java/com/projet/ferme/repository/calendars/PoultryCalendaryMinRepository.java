package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.PoultryCalendaryMin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PoultryCalendaryMinRepository extends JpaRepository<PoultryCalendaryMin, Long>{

	List<PoultryCalendaryMin> findByCategoryId(Long id);

}
