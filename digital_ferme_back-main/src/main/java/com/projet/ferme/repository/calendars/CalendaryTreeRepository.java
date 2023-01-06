package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.CalendaryTree;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendaryTreeRepository extends JpaRepository<CalendaryTree, Long>{

	List<CalendaryTree> findByTree_id(Long id);

}
