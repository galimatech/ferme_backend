package com.projet.ferme.repository.stocks;

import java.util.List;

import com.projet.ferme.entity.stocks.ShopStock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopStockRepository extends JpaRepository<ShopStock, Long>{

	List<ShopStock> findByProduct(String product);

	List<ShopStock> findByShop_id(Long shopId);

}
