package com.projet.ferme.service.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.projet.ferme.entity.comptability.UseFor;
import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.comptability.UseForRepository;
import com.projet.ferme.service.utile.MapResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UseForService {
    
    @Autowired
    private UseForRepository useForRepository;

    public Map<String, Object> addUseFor(UseFor useFor) throws NoElementAddException{

        if (!isValid(useFor).get(0)) {
            return new MapResponse().withSuccess(false)
            .withMessage("Veuillez ajouter une opération").response();
        }else if (!isValid(useFor).get(1)) {
            return new MapResponse().withSuccess(false)
            .withMessage("Veuillez ajouter un objet à réferencer").response();
        } else {
            UseFor savedUseFor = useForRepository.save(useFor);
            if (savedUseFor == null) {
                return new MapResponse().withSuccess(false)
                .withMessage("L'enregistrement a échoué").response();
            } else {
                return new MapResponse().withSuccess(true)
                .withObject(savedUseFor).withMessage("Enregistrement réussi").response();
            }
        }
    }

    public Map<String, Object> deleteUseFor(Long id)  throws NoElementFoundException{
        Optional<UseFor> userFor = useForRepository.findById(id);
        if (userFor.isPresent()) {
            useForRepository.deleteById(id);
            return new MapResponse().withSuccess(true)
            .withMessage("Supprimé avec succès").response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("L'enregistrement n'est pas dans la base").response();
        }
    }



    private List<Boolean> isValid(UseFor useFor){
        
        List<Boolean> booleans = new ArrayList<Boolean>();

        if (useFor.getOperation().equals(null)) {
            booleans.add(false) ;
        }else{
            booleans.add(true);
        }

        if (useFor.getBowl().equals(null) && useFor.getChickenCoop().equals(null) && 
            useFor.getEnclosure().equals(null) && useFor.getPlanting().equals(null)) {
                booleans.add(false);
        } else {
            booleans.add(true);
        }

        return booleans;
    }
}
