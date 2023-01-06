package com.projet.ferme.repository.outsubject;

import java.util.List;

import com.projet.ferme.entity.outsubject.OutPoultry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutPoultryRepository extends JpaRepository<OutPoultry,Long>{

	List<OutPoultry> findByPoultry_id(Long id);

}
