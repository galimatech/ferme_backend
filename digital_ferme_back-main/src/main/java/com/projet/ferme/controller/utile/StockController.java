package com.projet.ferme.controller.utile;

import java.util.Map;

import com.projet.ferme.entity.outsubject.IncomingStock;
import com.projet.ferme.entity.stocks.OutgoingStock;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.outsubject.IncomingService;
import com.projet.ferme.service.outsubject.OutgoingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StockController {

	@Autowired
	private IncomingService incomingService;
	@Autowired
	private OutgoingService outgoingService;
	
	@RequestMapping(value = "/api/v1/incoming", method = RequestMethod.POST)
	public Map<String, Object> postIncomingStock(@RequestBody IncomingStock stock){
			return incomingService.add(stock);
		
	}
	
	@RequestMapping(value = "/api/v1/incoming", method = RequestMethod.PUT)
	public Map<String, Object> putIncomingStock(@RequestBody IncomingStock stock){
			return incomingService.put(stock);
		
	}
	
	@RequestMapping(value = "/api/v1/incoming", method = RequestMethod.GET)
	public Map<String, Object> getByNameIncomingStock(){
		return incomingService.findAll();
	}
	
	@RequestMapping(value = "/api/v1/incoming/type/{type}", method = RequestMethod.GET)
	public Map<String, Object> getByTypeIncomingStock(@PathVariable("type") String type){
		return incomingService.getByType(type);
	}
	
	@RequestMapping(value = "/api/v1/incoming/{product}", method = RequestMethod.GET)
	public Map<String, Object> getIncomingStock(@PathVariable("product") String product){
		return incomingService.findByProduct(product);
	}
	
	@RequestMapping(value = "/api/v1/incoming/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteIncomingStock(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return incomingService.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}
	
	@RequestMapping(value = "/api/v1/outgoing", method = RequestMethod.POST)
	public Map<String, Object> postOutgoing(@RequestBody OutgoingStock stock){
			return outgoingService.add(stock) ;
		
	}
	
	@RequestMapping(value = "/api/v1/outgoing", method = RequestMethod.PUT)
	public Map<String, Object> putOutgoing(@RequestBody OutgoingStock stock) {
			return  outgoingService.put(stock);
		
	}
	
	@RequestMapping(value = "/api/v1/outgoing/produit", method = RequestMethod.GET)
	public Map<String, Object> getByProduitOutgoingStock(){
		return outgoingService.getProduitInStock();
	}
	
	@RequestMapping( value = "/api/v1/outgoing/product/detail/{product}", method = RequestMethod.GET)
	public Map<String, Object> getShopStockByProduct(@PathVariable("product") String product){
		return  outgoingService.getShopStockByProduct(product);
	}
	
	@RequestMapping(value = "/api/v1/outgoing/type/{type}", method = RequestMethod.GET)
	public Map<String, Object> getByTypeOutgoingStock(@PathVariable("type") String type){
		return outgoingService.getByType(type);
	}
	
	@RequestMapping(value = "/api/v1/outgoing/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteOutgoingStock(@PathVariable("id") Long id) {
			return outgoingService.deleteDirectly(id);
		
	}
	
	@RequestMapping(value = "/api/v1/outgoing/{produit}", method = RequestMethod.GET)
	public Map<String, Object> getByProduitStock(@PathVariable("produit") String produit){
		return outgoingService.getByProduit(produit);
	}
	
	@RequestMapping(value = "/api/v1/outgoing/sale", method = RequestMethod.POST)
	public Map<String, Object> addForSale(@RequestBody Map<String, Object> map){
			return outgoingService.addForSell(map);
		
	}
	
	@RequestMapping(value = "/api/v1/reverse", method = RequestMethod.POST)
	public Map<String, Object> reverse(@RequestBody Map<String, Object> map){
			String product = map.get("product").toString();
			String type = map.get("type").toString();
			String username = map.get("username").toString();
			int quantity = Integer.parseInt(map.get("quantity").toString());
			Long shopId = Long.parseLong(map.get("shopId").toString());
			
			return outgoingService.reverseInShop(product, quantity, type, username, shopId);
	
	}
	
	@RequestMapping(value = "api/v1/reverse/shoptoshop", method = RequestMethod.POST)
	public Map<String, Object> shopToShop(@RequestBody Map<String, Object> map){
			return outgoingService.shopToShop(map);
		
	}
	
	@RequestMapping(value = "/api/v1/shop/stock/{id}", method = RequestMethod.GET)
	public Map<String, Object> getShopStock(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return outgoingService.getShopStock(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
		
	}

	@RequestMapping(value="/api/v1/stock/byMe", method=RequestMethod.GET)
	public Map<String, Object> getStockByMe() {
		return outgoingService.makeByMe();
	}

	@RequestMapping(value="/api/v1/incoming/byMe", method=RequestMethod.GET)
	public Map<String, Object> getIncomingByMe() {
		return incomingService.makeByMe();
	}
	
}
