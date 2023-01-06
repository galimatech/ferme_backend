package com.projet.ferme.service.comptability;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.projet.ferme.entity.comptability.CategoryCompte;
import com.projet.ferme.entity.comptability.Compte;
import com.projet.ferme.entity.comptability.Operation;
import com.projet.ferme.entity.comptability.UseFor;
import com.projet.ferme.entity.person.Cashier;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.entity.utils.NewDate;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.category.CategoryCompteRepository;
import com.projet.ferme.repository.comptability.CompteRepository;
import com.projet.ferme.repository.comptability.OperationRepository;
import com.projet.ferme.repository.comptability.UseForRepository;
import com.projet.ferme.repository.person.CashierRepository;
import com.projet.ferme.repository.stocks.ShopRepository;
import com.projet.ferme.service.homesubject.AllHomeService;
import com.projet.ferme.service.utile.MapResponse;
import com.projet.ferme.service.utile.MapToObject;
import com.projet.ferme.service.utile.UserAuthenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
    
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private CategoryCompteRepository categoryCompteRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private UseForRepository useForRepository;
    @Autowired
    private AllHomeService allHomeService;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CashierRepository cashierRepository;
    @Autowired
    private SaleService saleService;
    @Autowired
    private UserAuthenticate userAuthenticate;

    public Map<String, Object> addOperation(Operation operation) {

        Operation savedOperation = operationRepository.save(operation);
        if (savedOperation != null) {
            return new MapResponse().withSuccess(true).withObject(savedOperation)
            .withMessage("Enregistrement réussi").response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("L'enregistrement a échoué Veuillez réessayer ").response();
        }
    }

    public Map<String, Object> updateOperation(Operation operation)  {
        
        if (operationRepository.findById(operation.getId()).isPresent()) {
            Operation savedOperation = operationRepository.save(operation);
            if (savedOperation != null) {
                return new MapResponse().withSuccess(true).withObject(savedOperation)
                .withMessage("Enregistrement réussi").response();
            } else {
                return new MapResponse().withSuccess(false)
                .withMessage("L'enregistrement a échoué Veuillez réessayer ").response();
            }
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Cette enregistrement n'est plus dans la base").response();
        }
    }

    public Map<String, Object> deleteOperation(Long id) throws NoElementFoundException{

        if (operationRepository.findById(id).isPresent()) {
            operationRepository.deleteById(id);
            return new MapResponse().withSuccess(true)
            .withMessage("Suppression réussit").response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Cette enregistrement n'est plus dans la base").response();
        }
    }

    public Map<String, Object> findAll(){
        Map<String,Object> map = allHomeService.getAllHome();
        List<UseFor> useFors = useForRepository.findAll();
        List<Operation> operations = operationRepository.findAll();
        if(operations.size() > 0)
            return new MapResponse().withSuccess(true)
            .withObject(operations).withArrayObject(map).withChildArrayObject(useFors)
            .withMessage(operations.size()+"enregistrements retrouvé").response();
        else
            return new MapResponse().withSuccess(true)
            .withObject(operations).withArrayObject(map).withChildArrayObject(useFors)
            .withMessage("aucune opération retrouvé").response();
    }

    public Map<String, Object> findByCompte(Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            List<Operation> operations = operationRepository.findByCompte_id(id);
            return new MapResponse().withSuccess(true)
            .withMessage(operations.size()+" enregistrement retrouvé")
            .withObject(compte.get()).withArrayObject(operations).response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Ce compte n'est plus dans la base").response();
        }
    }

    public Map<String, Object> findByCategory(Long id) {
        Optional<CategoryCompte> compte = categoryCompteRepository.findById(id);
        if (compte.isPresent()) {
            List<Operation> operations = operationRepository.findByCategory_id(id);
            return new MapResponse().withSuccess(true).withObject(compte.get())
            .withArrayObject(operations)
            .withMessage(operations.size()+" enregistrement réussit").response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Ce compte n'est plus dans la base").response();
        }
    }

   public Map<String,Object> addUseFor(UseFor useFor)  {
        if (!isValidUseFor(useFor).get(0)) {
            return new MapResponse().withSuccess(false).withMessage("Veuillez ajoutez une opération")
            .response();
        } else if(!isValidUseFor(useFor).get(1)){
            return new MapResponse().withSuccess(false).withMessage("Veuillez ajoutez un extra")
            .response();
        }else{
            UseFor savedUseFor = useForRepository.save(useFor);
            Operation operation = savedUseFor.getOperation();
            operation.setUseFor(savedUseFor);
            operationRepository.save(operation);
            return new MapResponse().withSuccess(true).withObject(operation)
            .withMessage("Enregistrement réussi").response();
        }
    }

    public Map<String, Object> shopToComtability(Map<String, Object> map){
        MapToObject mapToObject = new MapToObject(map);
        User user = userAuthenticate.getUserAuthenticate();
        Shop shop = shopRepository.findById(mapToObject.getLong("shopId")).get();
        Optional<Compte> compte = compteRepository.findByNumber("70700");
        if (compte.isPresent()) {
            Operation operation = new Operation();
            operation.setAmount(mapToObject.getInteger("payment"));
            operation.setComment("Versement de "+user.getFirstname()+" "+user.getLastname()+
            " pour la boutique "+shop.getName());
            operation.setCompte(compte.get());
            operation.setCreatedOn(new NewDate().getDate());
            operation.setUpdatedOn(new NewDate().getDate());
            operation.setDate(LocalDateTime.now());
            operation.setLabel("Vente des produits");
            Operation savedOperation = operationRepository.save(operation);
            if (savedOperation != null) {
                Boolean isKeep = amountForKeep(mapToObject.getInteger("keepAmount"), user, shop);
                if (isKeep) {
                    int counted = saleToCounted(user, shop);
                    return new MapResponse().withSuccess(true).withObject(savedOperation)
                    .withMessage("L'enregistrement à réussi et "+counted+
                    " ventes sont versés").response();
                } else {
                    operationRepository.delete(savedOperation);
                    return new MapResponse().withSuccess(false)
                    .withMessage("L'enregistrement à échoué").response();
                }
            }else
                return new MapResponse().withSuccess(false)
                .withMessage("L'enregistrement à échoué").response();
        }else{
            return new MapResponse().withSuccess(false).
            withMessage("Le compte vente de marchandise n'exist pas").response();
        }
    }

    private List<Boolean> isValidUseFor(UseFor useFor) {
        List<Boolean> booleans = new ArrayList<Boolean>();
        if (useFor.getOperation().equals(null)) {
            booleans.add(false);
            booleans.add(false);
        }else if (useFor.getBowl() == null && useFor.getChickenCoop() == null &&
            useFor.getEnclosure() == null && useFor.getPlanting() == null) {
            booleans.add(true);
            booleans.add(false);
        }  else {
            booleans.add(true);
            booleans.add(true);
        }
        return booleans;
    }

    private int saleToCounted(User user, Shop shop){
        Cashier cashier = cashierRepository.findByUser_idAndShop_id(user.getId(), shop.getId()).get();
        return saleService.findSaleToCount(cashier);
    }

    private boolean amountForKeep(int keepAmount,User user,Shop shop){
        if (keepAmount > 0) {
            //Capital social, appelé, non versé
            Optional<Compte> compte = compteRepository.findByNumber("1012");
            if(compte.isPresent()){
                Operation operation = new Operation();
                operation.setAmount(keepAmount);
                operation.setComment("Garder pour fond de caisse "+user.getFirstname()+" "+user.getLastname()+
                " pour la boutique "+shop.getName());
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
