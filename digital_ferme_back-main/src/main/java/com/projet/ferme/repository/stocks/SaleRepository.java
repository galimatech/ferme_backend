package com.projet.ferme.repository.stocks;

import java.util.List;

import com.projet.ferme.entity.stocks.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long>{

	Sale findBySubjectId(String subjectId);

    List<Sale> findByCashier_id(Long id);

    List<Sale> findByCustomer_id(Long id);

}
