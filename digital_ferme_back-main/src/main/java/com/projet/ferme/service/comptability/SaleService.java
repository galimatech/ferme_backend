package com.projet.ferme.service.comptability;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.comptability.Reimburse;
import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.entity.utils.NewDate;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.comptability.ReimburseRepository;
import com.projet.ferme.repository.person.CashierRepository;
import com.projet.ferme.repository.stocks.SaleRepository;
import com.projet.ferme.service.utile.MapResponse;
import com.projet.ferme.service.utile.MapToObject;
import com.projet.ferme.service.utile.UserAuthenticate;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	@Autowired
	private CashierRepository cashierRepository;
	@Autowired
	private ReimburseRepository reimburseRepository;
	@Autowired
	private UserAuthenticate userAuthenticate;
	
	public Map<String, Object> add(Sale s){
		Map<String, Object> returnMap = new HashMap<String,Object>();
		Sale sale = repository.save(s);
		if(sale == null) {
			returnMap.put("success", false);
			returnMap.put("message", "Echec de l'enregistrement");
		}else {
			returnMap.put("succes", true);
			returnMap.put("message", "Enregistré avec succé");
		}
		return returnMap;
	}
	
	public Sale findBySubject(String subjectId) {
		Sale sale = repository.findBySubjectId(subjectId);
		
		return sale;
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public int findSaleToCount(Cashier cashier){
		List<Sale> sales = repository.findByCashier_id(cashier.getId());
		sales = sales.stream().filter(s-> !s.isCounted()).
		map(s-> {s.setCounted(true); return s;}).collect(Collectors.toList());
		return repository.saveAll(sales).size();
	}

	public Map<String, Object> findNoReimburseSale(Long id) throws NoElementFoundException{
		List<Cashier> cashiers = cashierRepository.findByShop_id(id);
		List<Sale> sales = repository.findAll();
		sales = sales.stream().filter(s-> cashiers.contains(s.getCashier()) && !s.isReimburse()).collect(Collectors.toList());
		return new MapResponse().withSuccess(true).withObject(sales).response();
	}

	public Map<String, Object> reimburseSale(Map<String,Object> map){
		MapToObject mapToObject = new MapToObject(map);
		Long id = mapToObject.getLong("shopId");
		int amount = mapToObject.getInteger("amount");
		Optional<Sale> sale = repository.findById(id);
		if (sale.isEmpty()) {
			return new MapResponse().withSuccess(false).
			withMessage("La vente n'est pas retrouvé").response();
		} else {

			if(sale.get().getAccount()==amount){
				sale.get().setReimburse(true);
				sale.get().setUpdatedOn(new NewDate().getDate());
				repository.save(sale.get());
			}else{
				sale.get().setAdvance(sale.get().getAdvance()+amount);
				sale.get().setAccount(sale.get().getAccount()-amount);
				sale.get().setUpdatedOn(new NewDate().getDate());
				repository.save(sale.get());
			}
			boolean saveReimburse = saveReimburse(sale.get(),amount);
			if (saveReimburse) {
				return new MapResponse().withSuccess(true).withObject(sale.get()).
				withMessage("Remboursé avec succé").response();
			} else {
				repository.delete(sale.get());
				return new MapResponse().withSuccess(false).withObject(sale.get()).
				withMessage("Remboursement échoué veuillez éssayer encore").response();
			}
			
		}
	}

	private boolean saveReimburse(Sale sale,int amount){
		Reimburse reimburse = new Reimburse();
		reimburse.setCashier(getCashier(sale.getCashier().getShop()));
		reimburse.setCounted(false);
		reimburse.setCreatedOn(new NewDate().getDate());
		reimburse.setDate(LocalDateTime.now());
		reimburse.setId(null);
		reimburse.setSale(sale);
		reimburse.setAmount(amount);
		reimburse.setUpdatedOn(new NewDate().getDate());

		reimburse = reimburseRepository.save(reimburse);
		if (reimburse != null) {
			return true;
		}else return false;
	}

	private Cashier getCashier(Shop shop){
		User user = userAuthenticate.getUserAuthenticate();
		Cashier cashier = cashierRepository.findByUser_idAndShop_id(user.getId(), shop.getId()).get();
		return cashier;
	}
}
