package com.projet.ferme.controller.comptability;

import java.util.Map;

import com.projet.ferme.entity.comptability.CategoryCompte;
import com.projet.ferme.entity.comptability.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.projet.ferme.service.comptability.CompteService;


@RestController
public class CompteController {
    
    @Autowired
    private CompteService compteService;

    @RequestMapping(value="/api/v1/compte", method=RequestMethod.POST)
    public Map<String,Object> addCompte(@RequestBody Compte compte) {
            return compteService.addCompte(compte);
        
    }

    @RequestMapping(value="/api/v1/categoryCompte", method=RequestMethod.POST)
    public Map<String,Object> addCategory(@RequestBody CategoryCompte compte)  {
            return compteService.addCategory(compte);
        
    }

    @RequestMapping(value = "/api/v1/compte", method = RequestMethod.PUT)
    public Map<String,Object> putCompte(@RequestBody Compte compte) {
            return compteService.updateCompte(compte);
        
    }

    @RequestMapping(value = "/api/v1/categoryCompte", method = RequestMethod.PUT)
    public Map<String,Object> putCategory(@RequestBody CategoryCompte compte) {
            return compteService.updateCategory(compte);
        
    }

    @RequestMapping(value = "/api/v1/compte", method = RequestMethod.GET)
    public Map<String, Object> getCompte(){
        return compteService.findAllCompte();
    }
    
    @RequestMapping(value = "/api/v1/categoryCompte", method = RequestMethod.GET)
    public Map<String, Object> getCategory(){
        return compteService.findAllCategory();
    }

    @RequestMapping(value = "/api/v1/compte/Compte/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteCompte(@PathVariable("id") Long id) {
            return compteService.deleteCompte(id);
        
    }
    
    @RequestMapping(value = "/api/v1/categoryCompte/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteCategory(@PathVariable("id") Long id) {
            return compteService.deleteCategory(id);
        
    }
}
