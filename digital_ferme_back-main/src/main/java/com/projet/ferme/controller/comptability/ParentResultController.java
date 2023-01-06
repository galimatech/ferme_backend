package com.projet.ferme.controller.comptability;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.service.comptability.ParentResultService;

@RestController
public class ParentResultController {

    @Autowired
    private ParentResultService parentResulService;

    @RequestMapping(value = "api/v1/sumDetailsCompte", method = RequestMethod.GET)
    public Map<String, Object> sumDetailsCompte(){
        return parentResulService.findAllOperationDetails();
        
    }
    
}
 