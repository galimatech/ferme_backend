package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.CattleCalendaryMin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CattleCalendaryMinRepository extends JpaRepository<CattleCalendaryMin, Long>{

	List<CattleCalendaryMin> findByCategoryId(Long id);
}
