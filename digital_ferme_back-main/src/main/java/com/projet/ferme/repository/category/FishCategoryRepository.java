package com.projet.ferme.repository.category;

import com.projet.ferme.entity.category.FishCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin("*")
public interface FishCategoryRepository extends JpaRepository<FishCategory, Long>{

}
