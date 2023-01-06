package com.projet.ferme.service.outsubject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.person.Customer;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.OutgoingStock;
import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.entity.stocks.ShopStock;
import com.projet.ferme.entity.utils.NewDate;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.UserRepository;
import com.projet.ferme.repository.person.CashierRepository;
import com.projet.ferme.repository.person.CustomerRepository;
import com.projet.ferme.repository.stocks.OutgoingStockRepository;
import com.projet.ferme.repository.stocks.ShopRepository;
import com.projet.ferme.repository.stocks.ShopStockRepository;
import com.projet.ferme.service.comptability.SaleService;
import com.projet.ferme.service.utile.MapResponse;
import com.projet.ferme.service.utile.MapToObject;
import com.projet.ferme.service.utile.UserAuthenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutgoingService {

	@Autowired
	private OutgoingStockRepository repository;
	@Autowired
	private SaleService saleService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ShopRepository shopRepository;
	@Autowired
	private CashierRepository cashierRepository;
	@Autowired
	private ShopStockRepository shopStockRepository;
	@Autowired
	private UserAuthenticate userAuthenticate;
	@Autowired
	private CustomerRepository customerRepository;

	public Map<String, Object> add(OutgoingStock stock) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (stock.getType().equals("in")) {
			OutgoingStock newStock = repository.save(stock);
			if (newStock == null) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué");
			} else {
				returnMap.put("success", true);
				returnMap.put("message", "Enregistré avec succé");
				returnMap.put("stock", newStock);
			}
		} else {
			List<OutgoingStock> stocks = repository.findByProduit(stock.getProduit());

			Integer quantityReel = stocks.stream().filter(item -> item.getType().equals("in"))
					.mapToInt(item -> item.getQuantity()).sum()
					- stocks.stream().filter(item -> item.getType().equals("out")).mapToInt(item -> item.getQuantity())
							.sum();

			if (quantityReel < stock.getQuantity()) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué car la quantité demandé n'est pas disponible");
			} else {
				OutgoingStock newStock = repository.save(stock);
				if (newStock == null) {
					returnMap.put("success", false);
					returnMap.put("message", "L'enregistrement a échoué");
				} else {
					returnMap.put("success", true);
					returnMap.put("message", "Enregistré avec succé");
					returnMap.put("stock", newStock);
				}
			}
		}

		return returnMap;
	}

	public Map<String, Object> put(OutgoingStock stock) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		OutgoingStock oldStock = repository.getById(stock.getId());

		if (oldStock == null) {
			returnMap.put("success", false);
			returnMap.put("message", "L'enregistrement n'est plus dans la base");
		} else if (stock.getQuantity() != oldStock.getQuantity()) {
			returnMap.put("success", false);
			returnMap.put("message", "La modification n'est pas permise sur la quantité");
		} else {
			OutgoingStock updateStock = repository.save(stock);
			if (updateStock == null) {
				returnMap.put("success", false);
				returnMap.put("message", "La modification a échoué");
			} else {
				returnMap.put("success", true);
				returnMap.put("message", "Modifié avec succé");
				returnMap.put("stock", stock);
			}
		}
		return returnMap;
	}

	public Map<String, Object> getProduitInStock() {

		List<Map<String, Object>> maps = new ArrayList<>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<OutgoingStock> stocks = repository.findAll();

		stocks.stream().collect(Collectors.groupingBy(OutgoingStock::getProduit)).forEach((value, values) -> {
			Map<String, Object> xx = new HashMap<String, Object>();
			int actualy = values.stream().filter(s -> s.getType().equals("in")).mapToInt(v -> v.getQuantity()).sum()
					- values.stream().filter(s -> s.getType().equals("out")).mapToInt(v -> v.getQuantity()).sum();
			int inShop = getProductInShop(value);
			int all = actualy + inShop;
			xx.put("product", value);
			xx.put("actualy", actualy);
			xx.put("inShop",inShop);
			xx.put("quantity", all);
			maps.add(xx);
		});
		returnMap.put("success", true);
		returnMap.put("stocks", maps);

		return returnMap;
	}
	
	public Map<String, Object> getShopStockByProduct(String product) {

		List<Map<String, Object>> maps = new ArrayList<>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<ShopStock> stocks = shopStockRepository.findByProduct(product);
		List<Shop> shops = shopRepository.findAll();
		
		shops.forEach( shop -> {
			Map<String, Object> collectMap = new HashMap<String, Object>();
			int quantity = stocks.stream().filter(stock-> stock.getType().equals("in") && stock.getShop().equals(shop)).
			 mapToInt(stock->stock.getQuantity()).sum() -
			stocks.stream().filter(stock-> stock.getType().equals("out") && stock.getShop().equals(shop)).
			 mapToInt(stock->stock.getQuantity()).sum();
			collectMap.put("shop", shop);
			collectMap.put("quantity", quantity);
			maps.add(collectMap);
		});
		returnMap.put("success", true);
		returnMap.put("stocks", maps);

		return returnMap;
	}
	
	public Map<String, Object> getShopStock(Long shopId)  throws NoElementFoundException{
		//Shop shop = shopRepository.findById(shopId).get();
		Map<String, Object> map = new HashMap<String, Object>();
		List<ShopStock> shopStocks = shopStockRepository.findByShop_id(shopId);
		List<OutgoingStock> stocks = repository.findAll();
		List<String> product = shopStocks.stream().map(item-> item.getProduit()).distinct().collect(Collectors.toList());
		List<Object> groupStock = new ArrayList<Object>();
		product.forEach( current-> {
				Map<String, Object> collectStock = new HashMap<String, Object>();
				collectStock.put("product", current);
				collectStock.put("inShop", shopStocks.stream().filter(s -> s.getType().equals("in") && s.getProduit().equals(current)).mapToInt(v -> v.getQuantity()).sum()
						- shopStocks.stream().filter(s -> s.getType().equals("out") && s.getProduit().equals(current)).mapToInt(v -> v.getQuantity()).sum());
				collectStock.put("inStore", stocks.stream().filter(s -> s.getType().equals("in") && s.getProduit().equals(current))
						.mapToInt(v -> v.getQuantity()).sum()
						- stocks.stream().filter(s -> s.getType().equals("out") && s.getProduit().equals(current))
						.mapToInt(v -> v.getQuantity()).sum());
				groupStock.add(collectStock);
		});
		
		map.put("success", true);
		map.put("stocks", groupStock);
		
		return map;
	}

	public Map<String, Object> getByProduit(String produit) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<OutgoingStock> stocks = repository.findByProduit(produit);

		returnMap.put("success", true);
		returnMap.put("outgoingStocks", stocks);

		return returnMap;
	}

	public Map<String, Object> getByType(String type) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<OutgoingStock> stocks = repository.findByType(type);

		returnMap.put("success", true);
		returnMap.put("stocks", stocks);

		return returnMap;
	}

	public List<OutgoingStock> findBySubjectId(String subjectId) {
		List<OutgoingStock> stock = repository.findBySubjectId(subjectId);

		return stock;
	}

	public Map<String, Object> delete(Long id) throws NoElementFoundException{

		Map<String, Object> returnMap = new HashMap<String, Object>();

		repository.deleteById(id);
		returnMap.put("success", true);
		returnMap.put("message", "Supprimé avec succé");
		

		return returnMap;
	}

	public Map<String, Object> deleteDirectly(Long id) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		OutgoingStock stock = repository.getById(id);

		if (stock.getType().equals("in")) {
			returnMap.put("success", false);
			returnMap.put("message", "restriction sur les stocks entrants");
		} else {
			repository.deleteById(id);
			returnMap.put("success", true);
			returnMap.put("message", "Supprimé avec succé");
		}

		return returnMap;
	}

	public Map<String, Object> addForSell(Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Long idShop = Long.parseLong(map.get("shopId").toString());
		List<ShopStock> stocks = shopStockRepository.findByProduct(map.get("product").toString());
		stocks = stocks.stream().filter(item -> item.getShop().getId().equals(idShop)).collect(Collectors.toList());

		Integer quantityIn = stocks.stream().filter(item -> item.getType().equals("in"))
				.mapToInt(item -> item.getQuantity()).sum();

		Integer quantityOut = stocks.stream().filter(item -> item.getType().equals("out"))
				.mapToInt(item -> item.getQuantity()).sum();

		Integer quantityReel = quantityIn - quantityOut;

		Cashier cashier = getCashier(map.get("username").toString(),idShop);

		if (cashier == null) {
			returnMap.put("success", false);
			returnMap.put("message", "Vous n'etes pas un caissier");
		} else if (!cashier.isActive()) {
			returnMap.put("success", false);
			returnMap.put("message", "Votre accée a la caisse est désactivé veullier contacter l'administrateur");
		} else if (!cashier.isStatus()) {
			returnMap.put("success", false);
			returnMap.put("message", "Veulliez ouvrir une caisse svp");
		} else {

			if (quantityReel < Integer.parseInt(map.get("quantity").toString())) {
				returnMap.put("success", false);
				returnMap.put("message", "L'enregistrement a échoué car la quantité demandé n'est pas disponible");
			} else {
				ShopStock shopStock = new ShopStock();
				shopStock.setDescription("vente");
				shopStock.setProduct(map.get("product").toString());
				shopStock.setQuantity(Integer.parseInt(map.get("quantity").toString()));
				shopStock.setType("out");
				shopStock.setUser(cashier.getUser());
				shopStock.setCreatedOn(new NewDate().getDate());
				shopStock.setUpdatedOn(new NewDate().getDate());
				shopStock.setShop(cashier.getShop());
				
				ShopStock newStock = shopStockRepository.save(shopStock);
				if (newStock == null) {
					returnMap.put("success", false);
					returnMap.put("message", "L'enregistrement a échoué");
				} else {
					MapToObject mapToObject = new MapToObject(map);
					Customer customer = customerRepository.findById(mapToObject.getLong("customerId")).get();
					saveSale(null, shopStock, cashier,mapToObject.getString("advance"),
					mapToObject.getString("account"),mapToObject.getString("price"),
					mapToObject.getString("description"),customer);
					returnMap.put("success", true);
					returnMap.put("message", "Enregistré avec succé");
					returnMap.put("stock", newStock);
				}
			}
		}

		return returnMap;
	}

	private void saveSale(Long id, ShopStock stock, Cashier cashier,String advanceString,
	String accountString,String priceString,String description,Customer customer) {
		Sale sale = new Sale();
		sale.setId(id);
		sale.setProduit(stock.getProduit());
		sale.setQuantity(stock.getQuantity());
		sale.setAccount(Integer.parseInt(accountString));
		sale.setAdvance(Integer.parseInt(advanceString));
		sale.setPrice(Integer.parseInt(priceString));
		sale.setDescription(description);
		sale.setDate(LocalDateTime.now());
		sale.setCreatedOn(stock.getCreatedOn());
		sale.setUpdatedOn(stock.getUpdatedOn());
		sale.setCashier(cashier);
		sale.setCustomer(customer);
		if(Integer.parseInt(accountString)>0){
			sale.setReimburse(false);
		}else{
			sale.setReimburse(true);
		}
		saleService.add(sale);
	}

	private Cashier getCashier(String username, Long idShop) {
		Cashier cashier = new Cashier();
		Optional<User> user = userRepository.findByUsername(username);
		Optional<Shop> shop = shopRepository.findById(idShop);

		if (user.isPresent() && shop.isPresent()) {
			Optional<Cashier> cashierOptional = cashierRepository.findByUser_idAndShop_id(user.get().getId(),
					shop.get().getId());
			cashier = cashierOptional.get();
		}
		return cashier;
	}

	public Map<String, Object> reverseInShop(String product, int quantity, String type, String username, Long shopId) {
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> map = new HashMap<String, Object>();
		OutgoingStock outgoingStock = new OutgoingStock();
		ShopStock shopStock = new ShopStock();
		Shop shop = shopRepository.getById(shopId);
		outgoingStock.setDescription("reverse");
		outgoingStock.setProduit(product);
		outgoingStock.setQuantity(quantity);
		outgoingStock.setSubjectId("intern");
		outgoingStock.setType(type);
		outgoingStock.setUser(user);
		outgoingStock.setCreatedOn(new NewDate().getDate());
		outgoingStock.setUpdatedOn(new NewDate().getDate());

		shopStock.setDescription("reverse");
		shopStock.setProduct(product);
		shopStock.setQuantity(quantity);
		shopStock.setUser(user);
		shopStock.setCreatedOn(new NewDate().getDate());
		shopStock.setUpdatedOn(new NewDate().getDate());
		shopStock.setShop(shop);

		if (type.equals("in")) {

			List<ShopStock> shopStocks = shopStockRepository.findByProduct(product);
			Integer currentQuantity = shopStocks.stream().filter(item -> item.getType().equals("in"))
					.mapToInt(item -> item.getQuantity()).sum()
					- shopStocks.stream().filter(item -> item.getType().equals("out"))
							.mapToInt(item -> item.getQuantity()).sum();
			if (currentQuantity >= quantity) {
				outgoingStock.setComesFrom(shop.getName());
				OutgoingStock newStock = repository.save(outgoingStock);
				if (newStock == null) {
					map.put("success", false);
					map.put("status","no");
					map.put("message", "L'enregistrement a échoué");
				} else {

					shopStock.setType("out");
					ShopStock savedShopStock = shopStockRepository.save(shopStock);
					if (savedShopStock == null) {
						map.put("success", false);
						map.put("status","no");
						map.put("message", "L'enregistrement a échoué");
						repository.delete(newStock);
					} else {
						map.put("success", true);
						map.put("status","yes");
						map.put("message", "Enregistré avec succé");
						//map.put("shopStock", savedShopStock);
						//map.put("stock", newStock);
					}
				}
			} else {
				map.put("success", false);
				map.put("status","no");
				map.put("message", "Vous avez moin de " + quantity + " dans votre stock");
			}
		} else {
			List<OutgoingStock> stocks = repository.findByProduit(product);

			Integer currentQuantity = stocks.stream().filter(item -> item.getType().equals("in"))
					.mapToInt(item -> item.getQuantity()).sum()
					- stocks.stream().filter(item -> item.getType().equals("out")).mapToInt(item -> item.getQuantity())
							.sum();

			if (currentQuantity >= quantity) {

				OutgoingStock newStock = repository.save(outgoingStock);
				if (newStock == null) {
					map.put("success", false);
					map.put("status","no");
					map.put("message", "L'enregistrement a échoué");
				} else {

					shopStock.setType("in");
					ShopStock savedShopStock = shopStockRepository.save(shopStock);
					if (savedShopStock == null) {
						map.put("success", false);
						map.put("status","no");
						map.put("message", "L'enregistrement a échoué");
						repository.delete(newStock);
					} else {
						map.put("success", true);
						map.put("status","yes");
						map.put("message", "Enregistré avec succé");
						//map.put("shopStock", savedShopStock);
						//map.put("stock", newStock);
					}
				}
			} else {
				map.put("success", false);
				map.put("status","no");
				map.put("message", "Vous avez moin de " + quantity + " dans votre stock");
			}
		}

		return map;
	}
	
	public Map<String, Object> shopToShop(Map<String, Object> enterMap) {
		String product = enterMap.get("product").toString();
		int quantity = Integer.parseInt(enterMap.get("quantity").toString());
		Long senderShop = Long.parseLong(enterMap.get("senderShop").toString());
		Long receverShop = Long.parseLong(enterMap.get("receverShop").toString());
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userAuthenticate.getUserAuthenticate();
		map = reverseInShop(product, quantity, "in", user.getUsername(), senderShop);
		if( map.get("status").toString().equals("yes")){
			map = reverseInShop(product, quantity, "out", user.getUsername(), receverShop);
		}
		return map;
	}

	public Map<String,Object> makeByMe(){
		User user = userAuthenticate.getUserAuthenticate();
		List<OutgoingStock> outgoingStocks = repository.findByUser_id(user.getId());
		return new MapResponse().withSuccess(true).withObject(outgoingStocks).
		withMessage(outgoingStocks.size()+" enregistrements trouvés").response();
	}
	
	private int getProductInShop(String product) {
		List<ShopStock> stocks = shopStockRepository.findByProduct(product);
		int quantity = stocks.stream().filter(stock->stock.getType().equals("in")).mapToInt(stock->stock.getQuantity()).sum() -
		stocks.stream().filter(stock->stock.getType().equals("out")).mapToInt(stock->stock.getQuantity()).sum();
		return quantity;
	}

}
