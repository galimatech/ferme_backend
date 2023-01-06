package com.projet.ferme.repository.outsubject;

import java.util.List;

import com.projet.ferme.entity.outsubject.OutFish;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutFishRepository extends JpaRepository<OutFish, Long>{

	List<OutFish> findByFish_id(Long id);
}
