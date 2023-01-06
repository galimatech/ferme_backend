package com.projet.ferme.service.comptability;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projet.ferme.entity.comptability.Compte;
import com.projet.ferme.entity.comptability.Operation;
import com.projet.ferme.entity.comptability.Reimburse;
import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.person.CashierNew;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.Sale;
import com.projet.ferme.entity.utils.NewDate;
import com.projet.ferme.repository.comptability.CompteRepository;
import com.projet.ferme.repository.comptability.OperationRepository;
import com.projet.ferme.repository.comptability.ReimburseRepository;
import com.projet.ferme.repository.person.CashierNewRepository;
import com.projet.ferme.repository.person.CashierRepository;
import com.projet.ferme.repository.stocks.SaleRepository;
import com.projet.ferme.service.utile.MapResponse;
import com.projet.ferme.service.utile.MapToObject;
import com.projet.ferme.service.utile.UserAuthenticate;

@Service
public class OpenCloseShopService {
    
    @Autowired 
    private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private CashierRepository cashierRepository;
    @Autowired
    private CashierNewRepository cashierNewRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private UserAuthenticate userAuthenticate;
    @Autowired
    private ReimburseRepository reimburseRepository;

    // Retour l'état de la boutique
    public Map<String, Object> mainMethod(Map<String,Object> enterMap){
        MapToObject mapToObject = new MapToObject(enterMap);
        Optional<Cashier> cashier = getCashier(mapToObject.getLong("shopId"));
        if (cashier.isPresent()) {
            if(cashier.get().getAccess() == mapToObject.getInteger("access")){
                Map<String, Object> map = new HashMap<String, Object>();
                CashierNew cashierNew = getStatus(mapToObject.getLong("shopId"));
                if (cashierNew.isOpen()) {
                    map.put("status", cashierNew.isOpen());
                    map.put("cashier", cashierNew.getCashier());
                    int cash = getCash(cashier.get());
                    int reimburse = getamountReimburse(cashier.get());
                    if (cashierNew.isCounted()) {
                        cash = cash + reimburse;
                        map.put("cash", cash);
                    } else {
                        cash = cash + cashierNew.getCash() + reimburse;
                        map.put("cash", cash);
                    }
                    return new MapResponse().withObject(map).withSuccess(true)
                    .withMessage("Voulez vous fermer la caisse avec "+cash+"Fcfa ?")
                    .response();
                } else {    
                    map.put("status", cashierNew.isOpen());
                    map.put("cash", cashierNew.getCash());
                    map.put("cashier", cashierNew.getCashier());
                    return new MapResponse().withObject(map).withSuccess(true)
                    .withMessage("Voulez vous ouvrir la caisse avec "+cashierNew.getCash()+"Fcfa ?")
                    .response();
                }
            }else{
                return new MapResponse().withSuccess(false)
                .withMessage("Votre code d'accés n'est pas correct ")
                .response();
            }
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Vous n'etes pas cashier dans cette boutique")
            .response();
        }
    }

    // Ouvrir la boutique
    public Map<String,Object> openShop(Map<String,Object> enterMap){
        MapToObject mapToObject = new MapToObject(enterMap);
        if (!isOpen(mapToObject.getLong("shopId"))) {
            return new MapResponse().withSuccess(false).withMessage("La boutique est déja ouverte")
            .response();
        }else if(!isOpenByMe()){
            return new MapResponse().withSuccess(false).withMessage("Vous avez déja ouverte une boutique")
            .response();
        } else {
        Optional<Cashier> cashier = getCashier(mapToObject.getLong("shopId"));
        CashierNew cashierNew = addNews(cashier.get(), true, mapToObject.getInteger("cash"));
        if (cashierNew == null) {
            return new MapResponse().withSuccess(false)
            .withMessage("Une erreur c'est produite veuillez réessayer")
            .response();
        } else {
            return new MapResponse().withSuccess(true)
            .withObject(cashierNew).withMessage("Ouvert avec succés").response();
        }}
    }

    // Fermé la boutique
    public Map<String,Object> closeShop(Map<String,Object> enterMap){

        MapToObject mapToObject = new MapToObject(enterMap);
        
        Optional<Cashier> cashier = getCashier(mapToObject.getLong("shopId"));
        int cash = getCash(cashier.get());
        CashierNew cashierNew = getStatus(cashier.get().getShop().getId());
        int reimburse = getamountReimburse(cashier.get());
        if (!cashierNew.isCounted()) {
            cash = cash + cashierNew.getCash()+ reimburse;
        }else{
            cash = cash + reimburse;
        }
        updateNew(cashier.get());
        CashierNew savedCashierNew = addNews(cashier.get(), false, cash);
        if (savedCashierNew == null) {
            return new MapResponse().withSuccess(false)
            .withMessage("Une erreur c'est produite veuillez rééssayer")
            .response();
        } else {
            saveCash(cash, cashier.get());
            makeCountedReimburse(cashier.get());
            return new MapResponse().withSuccess(true)
            .withObject(cashierNew).withMessage("Fermé avec succé").response();
        }
    }

    // Calculer le montant a comptabilisé
    public Map<String,Object> amountToSave(Long shopId){
        Optional<Cashier> cashier = getCashier(shopId);
        CashierNew cashierNew = getStatus(shopId);
        int openAmount = 0;
        if (!cashierNew.isCounted()) {
            openAmount = cashierNew.getCash();
        }
        int reimburse = getamountReimburse(cashier.get());
        int sale = getCash(cashier.get());
        int total = openAmount + reimburse + sale;
        String msg = "Voulez vous verser "+sale+"Fcfa de vente et "+reimburse+"Fcfa de remboursement"+
        " avec un montant d'ouverture de "+openAmount+"Fcfa soit au total: "+total+"Fcfa";
        return new MapResponse().withSuccess(true).withMessage(msg).withObject(total).response();
    }

    // Verser les ventes dans le compte vente de marchandise
    public Map<String,Object> saveInComptability(Map<String,Object> map){
        MapToObject mapToObject = new MapToObject(map);
        Long shopId = mapToObject.getLong("shopId");
        int cash = mapToObject.getInteger("cash");
        Optional<Cashier> cashier = getCashier(shopId);
        CashierNew cashierNew = getStatus(shopId);
        int openAmount = 0;
        if (!cashierNew.isCounted()) {
            openAmount = cashierNew.getCash();
        }
        int reimburse = getamountReimburse(cashier.get());
        int sale = getCash(cashier.get());
        int payment = reimburse + sale + openAmount;
        Optional<Compte> compte = compteRepository.findByNumber("70700");
        if (compte.isPresent()) {
            Operation operation = new Operation();
            operation.setAmount(payment);
            operation.setComment("Versement de "+cashier.get().getUser().getFirstname()+" "+cashier.
            get().getUser().getLastname()+" pour la boutique "+cashier.get().getShop().getName());
            operation.setCompte(compte.get());
            operation.setCreatedOn(new NewDate().getDate());
            operation.setUpdatedOn(new NewDate().getDate());
            operation.setDate(LocalDateTime.now());
            operation.setLabel("Vente des produits");
            Operation savedOperation = operationRepository.save(operation);
            if (savedOperation != null) {
                Boolean isKeep = amountForKeep(cash, cashier.get());
                if (isKeep) {
                    makeCountedSale(cashier.get());
                    makeCountedReimburse(cashier.get());
                    updateNew(cashier.get());
                    return new MapResponse().withSuccess(true).withObject(savedOperation)
                    .withMessage("L'enregistrement a réussi et "+payment+
                    " ventes sont versés").response();
                } else {
                    operationRepository.delete(savedOperation);
                    return new MapResponse().withSuccess(false)
                    .withMessage("L'enregistrement a échoué").response();
                }
            }else
                return new MapResponse().withSuccess(false)
                .withMessage("L'enregistrement a échoué").response();
        }else{
            return new MapResponse().withSuccess(false).
            withMessage("Le compte vente de marchandise n'existe pas").response();
        }
    }
    
    // Recuperer le cashier en cour
    private Optional<Cashier> getCashier(Long shopId){
        User user = userAuthenticate.getUserAuthenticate();
        Optional<Cashier> cashier = cashierRepository.findByUser_idAndShop_id(user.getId(), shopId);
        return cashier;
    }

    // Recuperer la total des ventes non comptabilisé
    private int getCash(Cashier cashier){
        List<Sale> sales = saleRepository.findByCashier_id(cashier.getId());
        int cash = sales.stream().filter(sale -> !sale.isCounted())
        .mapToInt(sale -> sale.getAdvance()).sum();
        return cash;
    }

    //Comptabilise les ventes
    private void makeCountedSale(Cashier cashier){
        List<Sale> sales = saleRepository.findByCashier_id(cashier.getId());
        sales = sales.stream().filter(sale -> !sale.isCounted()).collect(Collectors.toList());
        List<Sale> transformSales = new ArrayList<Sale>();
        sales.stream().forEach(sale-> {sale.setCounted(true);transformSales.add(sale);});
        sales = saleRepository.saveAll(transformSales);
    }
    
    // Mettre a jour les remboursements apres comptabilité
    private void makeCountedReimburse(Cashier cashier){
        List<Reimburse> reimburses = reimburseRepository.findByCashier_id(cashier.getId());
        reimburses = reimburses.stream().filter(reimburse->!reimburse.isCounted()).collect(Collectors.toList());
        List<Reimburse> countedReimburses = new ArrayList<Reimburse>();
        reimburses.stream().forEach(reimburse->{reimburse.setCounted(true);countedReimburses.add(reimburse);});
        reimburseRepository.saveAll(countedReimburses);
    }

    // Recuperer le total des remboursement non comtabilise
    private int getamountReimburse(Cashier cashier){
        List<Reimburse> reimburses = reimburseRepository.findByCashier_id(cashier.getId());
        int amount = reimburses.stream().filter(reimburse-> !reimburse.isCounted())
        .mapToInt(reimburse-> reimburse.getSale().getAccount()).sum();
        return amount;
    }

    // Voir le dernier status de la caisse
    private CashierNew getStatus(Long shopId){
        List<Cashier> cashiers = cashierRepository.findByShop_id(shopId);
        List<CashierNew> cashierNews = cashierNewRepository.findAll();
        cashierNews = cashierNews.stream().filter(cashierNew->cashiers.contains(cashierNew.getCashier())).collect(Collectors.toList());
        if (cashierNews.size() == 0) {
            CashierNew cashierNew = new CashierNew();
            cashierNew.setCounted(true);
            cashierNew.setOpen(false);
            cashierNew.setCash(0);
            return cashierNew;
        } else {
            CashierNew cashierNew = cashierNews.stream().max(Comparator.comparing(CashierNew::getTime)).get();
            return cashierNew;
        }
    }
    
    // Ecrire le cash de la caisse dans la comptabilité
    private boolean saveCash(int amount,Cashier cashier){
        Optional<Compte> compte = compteRepository.findByNumber("5314");
        if(compte.isPresent()){
            Operation operation = new Operation();
            operation.setAmount(amount);
            operation.setComment("Argent dans la caisse par "+cashier.getUser().getFirstname()+" "+
             cashier.getUser().getLastname()+
            " pour la boutique "+cashier.getShop().getName());
            operation.setCompte(compte.get());
            operation.setCreatedOn(new NewDate().getDate());
            operation.setUpdatedOn(new NewDate().getDate());
            operation.setDate(LocalDateTime.now());
            operation.setLabel("Vente de produits non versé");
            Operation savedOperation = operationRepository.save(operation);
            if (savedOperation != null){
                makeCountedSale(cashier);
                return true;
            }else
                return false;
        }else{
            return false;
        }
    }

    // Enregistrer une ouverture ou une fermeture dans la base 
    private CashierNew addNews(Cashier cashier, boolean open,int cash) {
		
		CashierNew cashierNew = new CashierNew();

		cashierNew.setTime(LocalDateTime.now());
		cashierNew.setOpen(open);
		cashierNew.setCash(cash);
		cashierNew.setCashier(cashier);
        cashierNew.setCounted(false);
		
		CashierNew savedCashierNew = cashierNewRepository.save(cashierNew);
        cashier.setStatus(open);
        cashierRepository.save(cashier);
		return savedCashierNew;
	}

    // Comptabilise le dernier montant de l'ouverture de la caisse 
    private boolean updateNew(Cashier cashier){
            List<CashierNew> news = cashierNewRepository.findByCashier_id(cashier.getId());
            CashierNew cashierNew = news.stream().max(Comparator.comparing(CashierNew::getTime)).get();
            cashierNew.setCounted(true);
            CashierNew saveNew = cashierNewRepository.save(cashierNew);
            if (saveNew == null) {
                return false;
            } else {
                return true;
            }
    }

    // Return true si la boutique est ouverte et false si elle est fermé
    private boolean isOpen(Long shopId) {
		List<Cashier> cashiers = cashierRepository.findByShop_id(shopId);
		return cashiers.stream().noneMatch(item-> item.isStatus());
	}

    // Return true si aucune boutique n'est ouvert par l'utilisateur sinon elle return true
    private boolean isOpenByMe(){
        User user = userAuthenticate.getUserAuthenticate();
        List<Cashier> cashiers = cashierRepository.findByUser_id(user.getId());
        return cashiers.stream().noneMatch(cashier->cashier.isStatus());
    }

    // Calculer l'argent que l'utilisateur veux garder en caisse
    private boolean amountForKeep(int keepAmount,Cashier cashier){
        if (keepAmount > 0) {
            //Capital social, appelé, non versé
            Optional<Compte> compte = compteRepository.findByNumber("1012");
            if(compte.isPresent()){
                Operation operation = new Operation();
                operation.setAmount(keepAmount);
                operation.setComment("Garder pour fond de caisse "+cashier.getUser().getFirstname()+" "
                +cashier.getUser().getLastname()+
                " pour la boutique "+cashier.getShop().getName());
                operation.setCompte(compte.get());
                operation.setCreatedOn(new NewDate().getDate());
                operation.setUpdatedOn(new NewDate().getDate());
                operation.setDate(LocalDateTime.now());
                operation.setLabel("Vente des produits");
                Operation savedOperation = operationRepository.save(operation);
                if (savedOperation != null)
                    return true;
                else
                    return false;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}
