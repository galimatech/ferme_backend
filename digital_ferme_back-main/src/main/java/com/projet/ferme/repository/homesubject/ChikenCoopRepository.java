package com.projet.ferme.repository.homesubject;

import java.util.List;

import com.projet.ferme.entity.homesubject.ChickenCoop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
public interface ChikenCoopRepository extends JpaRepository<ChickenCoop,Long>{

	List<ChickenCoop> findByFree(boolean b);

}
