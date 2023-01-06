package com.projet.ferme.repository.subject;

import java.util.List;
import java.util.Optional;

import com.projet.ferme.entity.subject.Speculation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeculationRepository extends JpaRepository<Speculation,Long>{

	int countByPresent(boolean b);
	
	Optional<Speculation> findByName(String name);
	
	List<Speculation> findByPresent(boolean present);

	List<Speculation> findByPlanting_id(Long id);

	List<Speculation> findBySeed_id(Long id);
}
