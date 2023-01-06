package com.projet.ferme.repository.subject;

import java.util.List;

import com.projet.ferme.entity.subject.Egg;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EggRepository extends JpaRepository<Egg, Long>{

	List<Egg> findByPoultry_id(Long id);
}
