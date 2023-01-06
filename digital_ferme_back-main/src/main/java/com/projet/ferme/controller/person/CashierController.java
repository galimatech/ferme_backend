package com.projet.ferme.controller.person;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.person.CashierNewService;
import com.projet.ferme.service.person.CashierService;

@RestController
public class CashierController {

	@Autowired
	private CashierService service;
	@Autowired
	private CashierNewService newService;
	
	@RequestMapping(value = "/api/v1/shop", method = RequestMethod.POST)
	public Map<String, Object> postShop(@RequestBody Shop shop) {
			return service.addShop(shop);
		
	}
	
	@RequestMapping(value = "/api/v1/shop", method = RequestMethod.PUT)
	public Map<String, Object> putShop(@RequestBody Shop shop) throws NoElementFoundException{
			return service.updateShop(shop);
		
	}
	
	@RequestMapping(value = "/api/v1/shop", method = RequestMethod.GET)
	public Map<String, Object> getShop(){
		return  service.findAllShop();
	}
	
	@RequestMapping(value = "/api/v1/shop/cashier/{id}", method = RequestMethod.GET)
	public Map<String, Object> getCashierByShop(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return service.findCashierByShop(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}
	
	}
	
	@RequestMapping(value = "/api/v1/cashier", method = RequestMethod.POST)
	public Map<String, Object> postCashier(@RequestBody Cashier cashier) throws NoElementAddException{
		try {
			return service.addCashier(cashier);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementAddException();
		}

	}
	
	@RequestMapping(value = "/api/v1/cashier/status" , method = RequestMethod.POST)
	public Map<String, Object> shopOpenClose(@RequestBody Map<String, Object> map){	
			return service.StatusCashier(map);

	}
	
	@RequestMapping(value = "/api/v1/cashier/active/{id}",method = RequestMethod.GET)
	public Map<String, Object> activeCashier(@PathVariable("id") Long id){
			return service.activeCashier(id);

	}
	
	
	@RequestMapping(value = "/api/v1/news/{id}", method = RequestMethod.GET)
	public Map<String, Object> getNewByShop(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return newService.findNewByShop(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}

	}
	
	@RequestMapping(value = "/api/v1/sale/news/{id}", method = RequestMethod.GET)
	public Map<String, Object> getSaleNews(@PathVariable("id") Long id) throws NoElementFoundException{
		try {
			return service.saleNews(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}

	}

	@RequestMapping(value = "/api/v1/cashier/sale/news/{id}", method = RequestMethod.GET)
	public Map<String,Object> getSaleNewsByCashier(@PathVariable("id") Long id) throws NoElementFoundException {
		try {
			return service.saleByCashier(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoElementFoundException();
		}

	}
}
