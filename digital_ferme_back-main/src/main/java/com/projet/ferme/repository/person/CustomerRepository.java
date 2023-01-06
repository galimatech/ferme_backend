package com.projet.ferme.repository.person;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.ferme.entity.person.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    Optional<Customer> findByTelephone(int telephone);
}
