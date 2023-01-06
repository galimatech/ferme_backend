package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.TreeCalendaryMin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeCalendaryMinRepository extends JpaRepository<TreeCalendaryMin, Long>{

	List<TreeCalendaryMin> findByCategoryId(Long id);

}
