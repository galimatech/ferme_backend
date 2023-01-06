package com.projet.ferme.controller.comptability;

import java.util.Map;

import com.projet.ferme.entity.comptability.Operation;
import com.projet.ferme.entity.comptability.UseFor;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.service.comptability.OperationService;
import com.projet.ferme.service.comptability.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {
    
    @Autowired
    private OperationService operationService;
    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "api/v1/operation", method = RequestMethod.POST)
    public Map<String, Object> addOperation(@RequestBody Operation operation)  {

            return operationService.addOperation(operation);
    }

    @RequestMapping(value = "api/v1/operation", method = RequestMethod.PUT)
    public Map<String, Object> updateOperation(@RequestBody Operation operation)  {
            return operationService.updateOperation(operation);

    }

    @RequestMapping(value = "api/v1/operation/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteOperation(@PathVariable("id") Long id) throws NoElementFoundException{
        try {
            return operationService.deleteOperation(id);
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new NoElementFoundException();
        }

    }

    @RequestMapping(value = "api/v1/operation/category/{id}", method = RequestMethod.GET)
    public Map<String, Object> getBySecondary(@PathVariable("id") Long id) {
            return operationService.findByCategory(id);

    }

    @RequestMapping(value = "api/v1/operation/compte/{id}", method = RequestMethod.GET)
    public Map<String, Object> getByCompte(@PathVariable("id") Long id) {
            return operationService.findByCompte(id);
 
    }

    @RequestMapping(value = "/api/v1/operation", method = RequestMethod.GET)
    public Map<String, Object> getAll(){
        return operationService.findAll();
    }

    @RequestMapping(value = "api/v1/useFor", method = RequestMethod.POST)
    public Map<String,Object> addUseFor(@RequestBody UseFor useFor)  {
            return operationService.addUseFor(useFor);

    }

    @RequestMapping(value = "/api/v1/shop/comptability", method = RequestMethod.POST)
    public Map<String, Object> shopCashToComptability(@RequestBody Map<String, Object> map) {

            return operationService.shopToComtability(map);

    }

    @RequestMapping(value = "/api/v1/reimburse", method = RequestMethod.POST)
    public Map<String,Object> reimburseSale(@RequestBody Map<String,Object> map){
            return saleService.reimburseSale(map);


    }

    @RequestMapping(value = "/api/v1/reimburse/get/{id}", method = RequestMethod.GET)
    public Map<String,Object> reimbufindNoReimburseSalerseSale(@PathVariable("id") Long shopId) throws NoElementFoundException{
        try {
            return  saleService.findNoReimburseSale(shopId);
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new NoElementFoundException();
        }
       
    }
}
