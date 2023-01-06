package com.projet.ferme.repository.person;

import java.util.List;
import java.util.Optional;

import com.projet.ferme.entity.person.Cashier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CashierRepository extends JpaRepository<Cashier, Long>{

	Optional<Cashier> findByUser_idAndShop_id(Long id, Long id2);

	List<Cashier> findByShop_id(Long id);

    List<Cashier> findByUser_id(Long id);

}
