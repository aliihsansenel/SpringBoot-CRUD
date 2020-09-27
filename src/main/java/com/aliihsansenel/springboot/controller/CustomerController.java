package com.aliihsansenel.springboot.controller;


import com.aliihsansenel.springboot.model.Customer;
import com.aliihsansenel.springboot.repository.CustomerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository = new CustomerRepository();

    //GET MAPPINGS
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listAllCustomers(){
    	return Customer.arrayToJson(customerRepository.getAll());
    }
    @GetMapping(value = "/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showCustomerById(@PathVariable("id") Integer customerId){
    	return customerRepository.getById(customerId).objectToJson();
    }

    @GetMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showCustomerByParamId(@RequestParam(required = false, name = "id") Integer customerId) {
    	if(customerId == null) {
    		return listAllCustomers();
    	}
    	return customerRepository.getById(customerId).objectToJson();
    }
    
    //POST MAPPING
    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCustomer(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Customer c = (Customer) new Customer().jsonToObject(requestBody);
    	System.out.println("customers firstname" + c.firstname);
    	System.out.println("customers sirname" + c.sirname);
    	System.out.println("customers address" + c.address);
    	return customerRepository
    			.saveOne(c)
    			.objectToJson();
    }
    //PUT MAPPING
    @PutMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCustomer(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Customer c = (Customer) new Customer().jsonToObject(requestBody);
    	if(customerRepository.checkIfExist(c.customerId)) {
    		return customerRepository.updateOne(c).objectToJson();
    	}
    	return customerRepository
    			.saveOne(c)
    			.objectToJson();
    }
    //DELETE MAPPINGS
    @DeleteMapping(value = "/customer/{id}")
    public String deleteCustomerById(@PathVariable("id") Integer customerId) {
    	if(customerRepository.checkIfExist(customerId)) {
    		customerRepository.deleteOne(customerId);
    		return "success";
    	}
		return "fail";
    }
    @DeleteMapping(value = "/customer")
    public String deleteCustomerByParamId(@RequestParam("id") Integer customerId) {
    	if(customerRepository.checkIfExist(customerId)) {
    		customerRepository.deleteOne(customerId);
    		return "success";
    	}
		return "fail";
    }
    //OPTIONS MAPPING
    @RequestMapping(value = "/customer", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> preflight() {
    	System.out.println("Someone triggered this function.");
    	return new ResponseEntity<Void>( HttpStatus.OK );
    }
}
