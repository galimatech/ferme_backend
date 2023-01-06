package com.projet.ferme.repository.category;

import com.projet.ferme.entity.category.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
public interface ProductRepository extends JpaRepository<Product, Long>{

}
