package com.projet.ferme.service.person;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.person.CashierNew;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.person.CashierNewRepository;
import com.projet.ferme.repository.person.CashierRepository;

@Service
public class CashierNewService {

	@Autowired
	private CashierNewRepository repository;
	@Autowired
	private CashierRepository cashierRepository;
	
	public boolean add(User user, Shop shop, boolean open,int cash) {
		
		CashierNew cashierNew = new CashierNew();
		Optional<Cashier> cashierOptional = cashierRepository.findByUser_idAndShop_id(user.getId(), shop.getId());
		if(cashierOptional.isPresent()) {
			
		cashierNew.setTime(LocalDateTime.now());
		cashierNew.setOpen(open);
		cashierNew.setCash(cash);
		cashierNew.setCashier(cashierOptional.get());
		
		CashierNew savedCashierNew = repository.save(cashierNew);
		
		if(savedCashierNew == null) {
			System.out.println("Not Save");
			return false;
		}
		else {
			System.out.println("Save OK");
			return true;
		}
		}else {
			System.out.println("Cashier not found");
			return false;
		}
		
	}
	
	public Map<String, Object> findAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<CashierNew> cashierNews = repository.findAll();
		map.put("success", true);
		map.put("cashierNews", cashierNews);
		
		return map;
	}
	
	public Map<String, Object> findNewByShop(Long id) throws NoElementFoundException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Cashier> cashiers = cashierRepository.findByShop_id(id);
		List<CashierNew> cashierNews = repository.findAll();
		List<CashierNew> news = cashierNews.stream().filter(item -> cashiers.contains(item.getCashier())).collect(Collectors.toList());
		
		map.put("success", true);
		map.put("news", news);
		
		return map;
	}
}
