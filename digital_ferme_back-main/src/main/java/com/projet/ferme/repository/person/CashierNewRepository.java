package com.projet.ferme.repository.person;

import com.projet.ferme.entity.person.CashierNew;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CashierNewRepository extends JpaRepository<CashierNew, Long>{

    List<CashierNew> findByCashier_id(Long id);

}
