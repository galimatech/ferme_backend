package com.projet.ferme.repository.subject;

import java.util.List;
import java.util.Optional;

import com.projet.ferme.entity.subject.Tree;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree,Long>{

	int countByPresent(boolean b);

	Optional<Tree> findByName(String name);
	
	List<Tree> findByPresent(boolean b);

	List<Tree> findByPlanting_id(Long id);

	List<Tree> findByCategory_id(Long id);
}
