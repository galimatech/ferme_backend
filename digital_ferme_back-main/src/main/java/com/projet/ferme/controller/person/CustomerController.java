package com.projet.ferme.controller.person;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ferme.entity.person.Customer;
import com.projet.ferme.service.person.CustomerService;

@RestController
public class CustomerController {
   
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/api/v1/customer", method = RequestMethod.POST)
    public Map<String,Object> addCustomer(@RequestBody Customer customer)  {

            return customerService.addCustomer(customer);
    }

    @RequestMapping(value = "/api/v1/customer", method = RequestMethod.GET)
    public Map<String, Object> getAllCustomer() {
        return customerService.findAll();
    }

    @RequestMapping(value = "/api/v1/customer/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteCustomer(@PathVariable("id") Long id) {
            return customerService.delete(id);

    }

    /* @RequestMapping(value = "/api/v1/noreimburse/{id}", method = RequestMethod.GET)
    public Map<String,Object> getNoReimburseSale(@PathVariable("id") Long id){
        return customerService.getNoReimburseSale(id);
    } */
}
