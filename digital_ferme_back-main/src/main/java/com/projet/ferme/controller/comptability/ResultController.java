package com.projet.ferme.controller.comptability;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.projet.ferme.service.comptability.ResultService;

@RestController
public class ResultController {

    @Autowired 
    ResultService resultService;

    @RequestMapping(value = "api/v1/sumByCompte", method =RequestMethod.GET)
    public Map<String, Object> sumByCompte(){
        return resultService.findAllOperation();

    }

    
}
