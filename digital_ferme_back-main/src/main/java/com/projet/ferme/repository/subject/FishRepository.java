package com.projet.ferme.repository.subject;

import java.util.List;
import java.util.Optional;

import com.projet.ferme.entity.subject.Fish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="fish", path="fishs")
public interface FishRepository extends JpaRepository<Fish, Long>{

	int countByPresent(boolean b);
	
	Optional<Fish> findByName(String name);
	
	List<Fish> findByPresent(boolean b);

	List<Fish> findByCategory_id(Long id);

	List<Fish> findByBowl_id(Long id);
}
