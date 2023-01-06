package com.projet.ferme.service.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.entity.utils.NewDate;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.UserRepository;
import com.projet.ferme.repository.person.CashierRepository;
import com.projet.ferme.repository.stocks.SaleRepository;
import com.projet.ferme.repository.stocks.ShopRepository;
import com.projet.ferme.service.utile.MapResponse;
import com.projet.ferme.service.utile.UserAuthenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashierService {

	@Autowired
	private ShopRepository shopRepository;
	@Autowired
	private CashierRepository cashierRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CashierNewService newService;
	@Autowired
	private SaleRepository saleRepository;
	@Autowired
	private UserAuthenticate userAuthenticate;

	public Map<String, Object> addShop(Shop shop) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Shop savedShop = shopRepository.save(shop);
		if (savedShop == null) {
			map.put("success", false);
			map.put("message", "L'enregistrement a échoué");
		}else {
			map.put("success", true);
			map.put("shop", savedShop);
			map.put("message", "Enregistrement reussi");
		}
		return map;
	}
	
	public Map<String, Object> updateShop(Shop shop) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Optional<Shop> oldShop = shopRepository.findById(shop.getId());
		if (oldShop.isEmpty()) {
			map.put("success", false);
			map.put("message", "L'enregistrement a échoué, cette boutique n'existe pas");
		}else {
			shop.setUpdatedOn(new NewDate().getDate());
			Shop savedShop = shopRepository.save(shop);
			if (savedShop == null) {
				map.put("success", false);
				map.put("message", "L'enregistrement a échoué");
			}else {
				map.put("success", true);
				map.put("shop", savedShop);
				map.put("message", "Enregistrement reussi");
			}
		}
		return map;
	}
	
	public Map<String, Object> findAllShop(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Shop> shops = shopRepository.findAll();
		map.put("success", true);
		map.put("shops", shops);
		
		return map;
	}
	
	public Map<String, Object> addCashier(Cashier cashier)  throws NoElementAddException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Cashier> cashiers = cashierRepository.findAll();
		boolean exit = cashiers.stream().noneMatch(c -> c.getShop().getId().equals(cashier.getShop().getId())
				&& c.getUser().getId().equals(cashier.getUser().getId()));
		boolean roleCashier = cashier.getUser().getRole().getRoleName().equals("cashier");
		if (exit && roleCashier) {
			cashier.setAcess(generateAccess());
			Cashier savedCashier = cashierRepository.save(cashier);
			if (savedCashier == null) {
				map.put("success", false);
				map.put("message", "L'enregistrement a échoué");
			}else {
				map.put("success", true);
				map.put("cashier", savedCashier);
				map.put("message", "Enregistrement réussi");
			}
		}else {
			map.put("success", false);
			map.put("message", "L'enregistrement a échoué, ce caissier est déjà dans cette boutique ou son "
					+ "profil n'est pas caissier");
		}
		return map;
	}
	
	public Map<String, Object> activeCashier(Long id) {
		Optional<Cashier> cashierOptional = cashierRepository.findById(id);
		if (cashierOptional.isPresent()) {
			cashierOptional.get().setActive(!cashierOptional.get().isActive());
			cashierOptional.get().setUpdatedOn(new NewDate().getDate());
			Cashier savedCashier = cashierRepository.save(cashierOptional.get());
			if (savedCashier == null)
				return response(false, "La mise à jour a échoué");
			else
				return response(true, "Mise a jour avec succès");
		}else
			return response(false, "Cet utilisateur n'exite plus");
	}
	
	public Map<String, Object> StatusCashier(Map<String, Object> inputMap){
	
		String userName = inputMap.get("username").toString();
		int access = Integer.parseInt(inputMap.get("access").toString());
		int cash = Integer.parseInt(inputMap.get("cash").toString());
		Shop shop = new Shop();
		shop.setId(Long.parseLong(inputMap.get("id").toString()));
		shop.setName(inputMap.get("name").toString());
		Map<String, Object> map = new HashMap<String, Object>();
		Optional<User> userOptional = userRepository.findByUsername(userName);
		if (userOptional.isPresent()) {
			Optional<Cashier> cashierOptional = cashierRepository.findByUser_idAndShop_id(userOptional.get().getId(), shop.getId());
		if (cashierOptional.isEmpty()) {
			map=response(false, "Il semble que le cassier n'existe pas");
		}else if (!cashierOptional.get().isActive()) {
			map=response(false, "Votre compte caissier est desactivé ");
		}else {
			if(access == cashierOptional.get().getAccess()) {
				if(cashierOptional.get().isStatus()) {
					map=open(cashierOptional.get(), false, "Votre caisse est fermée", shop, cash);
				}else {
					if(isOpen(shop.getId())) {
						map=open(cashierOptional.get(), true, "Votre caisse est ouverte", shop, cash);
					}else {
						map=response(false, "La caisse est déjà ouverte");
					}
				}
			}else {
				map=response(false, "Votre code PIN est incorret");
			}
		}
		}else {
			map=response(false,"Il semble que le cassier n'existe pas");
		}
		return map;
	}
	
	public Map<String, Object> findCashierByShop (Long shopId) throws NoElementFoundException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Cashier> cashiers = cashierRepository.findAll();
		Shop shop = shopRepository.findById(shopId).get();
		List<Cashier> cashier = cashiers.stream().filter( c -> c.getShop().getId().equals(shopId)).collect(Collectors.toList());
		List<User> users = cashier.stream().map( c -> c.getUser()).collect(Collectors.toList());
		List<User> allUsers = userRepository.findAll().stream().filter(user-> user.getRole().getRoleName().equals("cashier")).collect(Collectors.toList());;
		List<User> notUsers = allUsers.stream().filter(user-> !users.contains(user)).collect(Collectors.toList());
		map.put("success", true);
		map.put("shop", shop);
		map.put("users", users);
		map.put("cashiers", cashier);
		map.put("unusedCashiers", notUsers);
		
		return map;
	}
	
	private boolean isOpen(Long shopId) {
		List<Cashier> cashiers = cashierRepository.findByShop_id(shopId);
		return cashiers.stream().noneMatch(item-> item.isStatus());
	}
	
	private Map<String, Object> open(Cashier cashier,boolean status,String message,Shop shop,int cash) {
		Map<String, Object> map = new HashMap<String, Object>();
		cashier.setStatus(!cashier.isStatus());
		cashier.setUpdatedOn(new NewDate().getDate());
		Cashier savedCashier = cashierRepository.save(cashier);
		if(savedCashier == null) {
			map=response(false, "la mise a jour a échoué essayez encore");
		}else {
			newService.add(cashier.getUser(),shop,status,cash);
			map=response(true, message);
			map.put("status", status);
		}
		return map;
	}
	
	public Map<String, Object> saleNews(Long id) throws NoElementFoundException{
		Shop shop = shopRepository.findById(id).get();
		List<Cashier> cashiers = cashierRepository.findAll();
		final List<Cashier> selectcashiers = cashiers.stream().filter(cashier->cashier.getShop().getId()==id).collect(Collectors.toList());
		List<Sale> sales = saleRepository.findAll();
		sales = sales.stream().filter(sale-> selectcashiers.contains(sale.getCashier())).collect(Collectors.toList());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("shop", shop);
		map.put("sales", sales);
		return map;
	}

	public Map<String, Object> saleByCashier(Long shopId)  throws NoElementFoundException{
		User user = userAuthenticate.getUserAuthenticate();
		List<Cashier> cashiers = cashierRepository.findAll();
		
		Cashier cashier = cashiers.stream().filter(c -> c.getShop().getId().equals(shopId) &&
		 c.getUser().getId().equals(user.getId())).findFirst().get();

		List<Sale> sales = saleRepository.findAll();
		List<Sale> noCountedSales = sales.stream().filter(s -> s.getCashier().equals(cashier) && !s.isCounted()).collect(Collectors.toList());
		List<Sale> allSales = sales.stream().filter(s -> s.getCashier().equals(cashier)).collect(Collectors.toList());

		return new MapResponse().withSuccess(true).withObject(cashier).withArrayObject(noCountedSales).
		withChildArrayObject(allSales).withMessage(sales.size()+" Enregistrements retrouvés").response();
	}
	
	private static int generateAccess() {
		
		Random random = new Random();
		int result = 0;
		while (result<1000) {
			result =  random.nextInt(10000);
		}
		return result;
	}
	
	private Map<String, Object> response(boolean statusBoolean,String messageString) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", statusBoolean);
		map.put("message", messageString);
		return map;
	}
}
