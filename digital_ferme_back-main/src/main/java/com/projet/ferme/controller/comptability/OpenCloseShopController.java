package com.projet.ferme.controller.comptability;

import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.service.comptability.OpenCloseShopService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class OpenCloseShopController {

    @Autowired
    private OpenCloseShopService openCloseShopService;
   
    @RequestMapping(value="/api/v1/shopStatus", method=RequestMethod.POST)
    public Map<String,Object> getStatus(@RequestBody Map<String,Object> map) {
            return openCloseShopService.mainMethod(map);

    }

    @RequestMapping(value="/api/v1/shopStatus/open", method=RequestMethod.POST)
    public Map<String,Object> openStatus(@RequestBody Map<String,Object> map) {

            return openCloseShopService.openShop(map);

    }

    @RequestMapping(value="/api/v1/shopStatus/close", method=RequestMethod.POST)
    public Map<String,Object> closeStatus(@RequestBody Map<String,Object> map) {

            return openCloseShopService.closeShop(map);
    }

    @RequestMapping(value = "/api/v1/amountToSave/{id}",method = RequestMethod.GET)
    public Map<String,Object> amountToSave(@PathVariable("id") Long shopId){
            return openCloseShopService.amountToSave(shopId);

    }

    @RequestMapping(value = "/api/v1/makeInCompatability",method = RequestMethod.POST)
    public Map<String,Object> makeInComptability(@RequestBody Map<String,Object> map){

            return openCloseShopService.saveInComptability(map);

    }
    
}
