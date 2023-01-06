package com.projet.ferme.service.person;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.person.Customer;
import com.projet.ferme.repository.person.CustomerRepository;
import com.projet.ferme.service.utile.MapResponse;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    public Map<String,Object> addCustomer(Customer customer) {
        
        if (customerRepository.findByTelephone(customer.getTelephone()).isEmpty()) {
            Customer savedCustomer = customerRepository.save(customer);
            if (savedCustomer == null) {
                return new MapResponse().withSuccess(false).
                withMessage("L'enregistrement a échoué réessayez svp !").response();
            }else{
                return new MapResponse().withSuccess(true).withObject(savedCustomer).
                withMessage("L'enregistrement a réussi !").response();
            }
        }else{
            return new MapResponse().withSuccess(false).
            withMessage("Le client existe déja !").response();
        }
    }

    public Map<String,Object> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return new MapResponse().withSuccess(true).withObject(customers)
        .withMessage(customers.size()+" enrégistrements trouvés").response();
    }

    public Map<String, Object> delete(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return new MapResponse().withSuccess(true).
            withMessage("Supprimé avec succés").response();
        } else {
            return new MapResponse().withSuccess(false)
            .withMessage("Le client n'est pas dans la base de données").response();
        }
    }
}
