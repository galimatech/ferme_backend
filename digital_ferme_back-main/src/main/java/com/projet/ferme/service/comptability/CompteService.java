package com.projet.ferme.service.comptability;
import java.util.List;
import java.util.Map;

import com.projet.ferme.entity.comptability.CategoryCompte;
import com.projet.ferme.entity.comptability.Compte;
import com.projet.ferme.repository.category.CategoryCompteRepository;
import com.projet.ferme.repository.comptability.CompteRepository;
import com.projet.ferme.service.utile.MapResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;
    
    @Autowired
	private CategoryCompteRepository categoryCompteRepository;

    public Map<String, Object> addCompte(Compte compte)  {

        Compte compteSaved = compteRepository.save(compte);

        if (compteSaved != null) {
            return new MapResponse().withSuccess(true)
                    .withMessage("Enregistrement réussit").withObject(compteSaved).response();
        } else {
            return new MapResponse().withSuccess(false)
                    .withMessage("Enregistrement échoué").response();
        }
    }

    public Map<String, Object> updateCompte(Compte compte) {

        if (compteRepository.findById(compte.getId()).isPresent()) {
            Compte compteSaved = compteRepository.save(compte);

            if (compteSaved != null) {
                return new MapResponse().withSuccess(true)
                        .withMessage("Enregistrement réussi").withObject(compteSaved).response();
            } else {
                return new MapResponse().withSuccess(false)
                        .withMessage("Enregistrement échoué").response();
            }
        } else {
            return new MapResponse().withSuccess(false)
                    .withMessage("Votre enregistrement n'est plus dans la base").response();
        }
    }

    public Map<String, Object> deleteCompte(Long id) {
        if (compteRepository.findById(id).isPresent()) {
            compteRepository.deleteById(id);
            return new MapResponse().withSuccess(true)
                    .withMessage("Suppression réussit").response();
        } else {
            return new MapResponse().withSuccess(false)
                    .withMessage("Votre enregistrement n'est plus dans la base").response();
        }
    }

    public Map<String, Object> findAllCompte(){
        List<Compte> comptes = compteRepository.findAll();
        return new MapResponse().withSuccess(true).withMessage("Enregistrements trouvés "+comptes.size())
        .withObject(comptes).response();
    }

    public Map<String, Object> addCategory(CategoryCompte category) {

        CategoryCompte savedCategory = categoryCompteRepository.save(category);
        if (savedCategory != null) {
            return new MapResponse().withSuccess(true).withObject(savedCategory)
            .withMessage("Enregistrement réussit").response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("L'enregistrement a échoué Veuillez réessayer ").response();
        }
    }

    public Map<String, Object> updateCategory(CategoryCompte category)  {
        
        if (categoryCompteRepository.findById(category.getId()).isPresent()) {

            CategoryCompte savedCategory = categoryCompteRepository.save(category);
            if (savedCategory != null) {
                return new MapResponse().withSuccess(true).withObject(savedCategory)
                .withMessage("Enregistrement réussit").response();
            } else {
                return new MapResponse().withSuccess(false)
                .withMessage("L'enregistrement a échoué Veuillez réessayer ").response();
            }
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Cette enregistrement n'est plus dans la base").response();
        }
    }

    public Map<String, Object> deleteCategory(Long id) {

        if (categoryCompteRepository.findById(id).isPresent()) {
            categoryCompteRepository.deleteById(id);
            return new MapResponse().withSuccess(true)
            .withMessage("Suppression réussi").response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Cette enregistrement n'est plus dans la base").response();
        }
    }

    public Map<String, Object> findAllCategory(){
        List<Compte> comptes = compteRepository.findAll();
        List<CategoryCompte> categorys = categoryCompteRepository.findAll();
        return new MapResponse().withSuccess(true)
        .withMessage(categorys.size() + " enregistrements retrouvés")
        .withObject(comptes)
        .withArrayObject(categorys)
        .response();
    }
}
