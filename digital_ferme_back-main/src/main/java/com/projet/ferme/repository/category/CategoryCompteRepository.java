package com.projet.ferme.repository.category;

import java.util.List;

import com.projet.ferme.entity.comptability.CategoryCompte;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryCompteRepository extends JpaRepository<CategoryCompte, Long>{

    List<CategoryCompte> findByParent_id(Long id);
    
}
