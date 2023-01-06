package com.projet.ferme.repository.subject;

import java.util.List;
import java.util.Optional;

import com.projet.ferme.entity.subject.Cattle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CattleRepository extends JpaRepository<Cattle,Long>{

	Page<Cattle> findAll(Pageable pageable);
	
	Optional<Cattle> findByName(String name);
	
	List<Cattle> findByCategory_id(Long id);
	
	int countByPresent(boolean b);

	List<Cattle> findByPresent(boolean b);

	List<Cattle> findByEnclosure_id(Long id);
}
