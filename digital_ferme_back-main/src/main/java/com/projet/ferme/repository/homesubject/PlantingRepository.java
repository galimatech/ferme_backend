package com.projet.ferme.repository.homesubject;

import java.util.List;

import com.projet.ferme.entity.homesubject.Planting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantingRepository extends JpaRepository<Planting, Long>{

	List<Planting> findByFree(boolean b);

}
