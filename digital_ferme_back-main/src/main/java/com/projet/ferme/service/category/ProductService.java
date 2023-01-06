package com.projet.ferme.service.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.category.Product;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.category.ProductRepository;
import com.projet.ferme.service.outsubject.IncomingService;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private IncomingService incomingService;

	public Map<String, Object> add(Product product) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (product != null) {
			Product prod = repository.save(product);
			if (prod == null) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué");
			}else {
				returnMap.put("success", true);
				returnMap.put("product", prod);
				returnMap.put("message", "Enregistré avec succès");
			}
		}else {
			returnMap.put("success", false);
			returnMap.put("message", "L'enregistrement a échoué car votre object produit est null");
		}
		return returnMap;
	}
	
	public Map<String, Object> update(Product product) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Product prod = repository.getById(product.getId());
		if(prod == null) {
			returnMap.put("success", false);
			returnMap.put("message", "Ce produit n'est plus dans la base");
		}else {
			prod = repository.save(product);
			if (prod == null) {
				returnMap.put("success", false);
				returnMap.put("message", "La modification a échoué");
			}else {
				returnMap.put("success", true);
				returnMap.put("product", product);
				returnMap.put("message", "Modifié avec succé");
			}
		}
		return returnMap;
	}
	
	public Map<String, Object> findAll(){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Product> prods = repository.findAll();
		List<Map<String, Object>> products = new ArrayList<>();
		
		for (Product prod : prods) {
			Map<String, Object> product = new HashMap<String, Object>();
			Integer result = incomingService.getByProduct(prod.getProductName());
			product.put("id", prod.getId());
			product.put("productName", prod.getProductName());
			product.put("createdOn", prod.getCreatedOn());
			product.put("updatedOn", prod.getUpdatedOn());
			product.put("quantity", result);
			products.add(product);
		}
		returnMap.put("success", true);
		returnMap.put("products", products);
		
		return returnMap;
	}
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Product prod = repository.getById(id);
		if(prod == null) {
			returnMap.put("success", false);
			returnMap.put("message", "Ce produit n'est plus dans la base");
		}else {
			repository.delete(prod);
			returnMap.put("success", true);
			returnMap.put("message", "Supprimé avec succés");

		}
		return returnMap;
	}
	
}
