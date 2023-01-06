package com.projet.ferme.service.outsubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.outsubject.IncomingStock;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.stocks.IncomingStockRepository;
import com.projet.ferme.service.utile.MapResponse;
import com.projet.ferme.service.utile.UserAuthenticate;

@Service
public class IncomingService {

	@Autowired
	private IncomingStockRepository repository;
	@Autowired
	private UserAuthenticate userAuthenticate;
	
	public Map<String, Object> add(IncomingStock stock){
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		stock.setQuantity(stock.getValue()*stock.getVolume());
		stock.setUser(user);
		if(stock.getType().equals("in")) {
			IncomingStock newStock = repository.save(stock);
			if(newStock == null) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué");
			}else {
				returnMap.put("success", true);
				returnMap.put("message", "Enregistré avec succé");
				returnMap.put("stock", newStock);
			}
		}else {
			List<IncomingStock> stocks = repository.findAll();
			stocks = stocks.stream().filter(item->item.getProduct().equals(stock.getProduct())).collect(Collectors.toList());
			Integer quantityIn = stocks.stream().filter(item -> item.getType().equals("in"))
					.mapToInt(item -> item.getQuantity()).sum();
			Integer quantityOut = stocks.stream().filter(item -> item.getType().equals("out"))
					.mapToInt(item -> item.getQuantity()).sum();
			Integer quantityReel = quantityIn - quantityOut;
			
			if(quantityReel < stock.getQuantity()) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué car la quantité demandé n'est pas disponible");
			}else {
				IncomingStock newStock = repository.save(stock);
				if(newStock == null) {
					returnMap.put("success", false);
					returnMap.put("message", "L'enregistrement a échoué");
				}else {
					returnMap.put("success", true);
					returnMap.put("message", "Enregistré avec succé");
					returnMap.put("stock", newStock);
				}
			}
			
		}
		
		return returnMap;
	}
	
	public Map<String, Object> put(IncomingStock stock) {
		stock.setQuantity(stock.getValue()*stock.getVolume());
		User user = userAuthenticate.getUserAuthenticate();
		stock.setUser(user);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		IncomingStock oldStock = repository.findById(stock.getId()).get();
		if(oldStock == null) {
			returnMap.put("success", false);
			returnMap.put("message", "La modification a échoué car l'enregistrement n'est plus dans la base ");
		}else if(!oldStock.getQuantity().equals(stock.getQuantity())) {
			returnMap.put("success", false);
			returnMap.put("message", "L'enregistrement a échoué, car la quantité n'est pas modifiable");
		}else {
			IncomingStock newStock = repository.save(stock);
			if(newStock == null) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué");
			}else {
				returnMap.put("success", true);
				returnMap.put("message", "Enregistré avec succé");
				returnMap.put("stock", stock);
			}
		}
		
		return returnMap;
	}
	
	public Map<String, Object> getByType(String type){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<IncomingStock> stocks = repository.findByType(type);
		
		returnMap.put("success", true);
		returnMap.put("stocks", stocks);
		
		return returnMap;
	}
	
	public Map<String, Object> findByProduct(String product) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<IncomingStock> stocks = repository.findAll();
		stocks = stocks.stream().filter(item->item.getProduct().equals(product)).collect(Collectors.toList());
		returnMap.put("success", true);
		returnMap.put("stocks", stocks);
		
		return returnMap;
	}
	
	public Map<String, Object> findAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<IncomingStock> stocks = repository.findAll();
		List<String> products = stocks.stream().map(stock-> stock.getProduct()).distinct().collect(Collectors.toList());
		List<Object> objects = new ArrayList<>();
		products.forEach(current-> {
			Map<String, Object> collectMap = new HashMap<String, Object>();
			String unitString = stocks.stream().filter(stock-> stock.getProduct().equals(current)).findFirst().get().getUnitVolume();
			String category = stocks.stream().filter(stock-> stock.getProduct().equals(current)).findFirst().get().getCategory();
			String unitValue = stocks.stream().filter(stock-> stock.getProduct().equals(current)).findFirst().get().getUnitValue();
			collectMap.put("product", current);
			collectMap.put("quantity", getByProduct(current));
			collectMap.put("unit",unitString);
			collectMap.put("category", category);
			collectMap.put("unitValue", unitValue);
			objects.add(collectMap);
		});
		map.put("success", true);
		map.put("products", products);
		map.put("stocks", objects);
		
		return map;
	}
	
	public Integer getByProduct(String product){
	  List<IncomingStock> stocks = repository.findAll();
	  stocks = stocks.stream().filter(item->item.getProduct().equals(product)).collect(Collectors.toList());
		
		Integer quantityIn = stocks.stream().filter(item -> item.getType().equals("in"))
				.mapToInt(item -> item.getQuantity()).sum();
		Integer quantityOut = stocks.stream().filter(item -> item.getType().equals("out"))
				.mapToInt(item -> item.getQuantity()).sum();
		Integer quantityReel = quantityIn - quantityOut;
	  
	  return quantityReel; 
	  }
	
	public Map<String, Object> delete(Long id) throws NoElementFoundException{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		repository.deleteById(id);
		returnMap.put("success", true);
		returnMap.put("message", "Supprimé avec succé");
		
		return returnMap;
	}

	public Map<String,Object> makeByMe(){
		User user = userAuthenticate.getUserAuthenticate();
		List<IncomingStock> incomingStocks = repository.findByUser_id(user.getId());
		return new MapResponse().withSuccess(true).withObject(incomingStocks).
		withMessage(incomingStocks.size()+" enregistrements trouvés").response();
	}
	
}
