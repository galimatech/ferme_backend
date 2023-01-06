package com.projet.ferme.repository.comptability;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.ferme.entity.comptability.Reimburse;

public interface ReimburseRepository extends JpaRepository<Reimburse,Long>{

    List<Reimburse> findByCashier_id(Long id);
    
}
