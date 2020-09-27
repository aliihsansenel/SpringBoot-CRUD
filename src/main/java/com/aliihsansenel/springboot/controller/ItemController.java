package com.aliihsansenel.springboot.controller;

import com.aliihsansenel.springboot.model.Item;
import com.aliihsansenel.springboot.repository.ItemRepository;

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
public class ItemController {

    private ItemRepository itemRepository = new ItemRepository();

    //GET MAPPINGS
    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listAllCustomers(){
    	return Item.arrayToJson(itemRepository.getAll());
    }
    @GetMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showCustomerById(@PathVariable("id") Integer itemId){
    	return itemRepository.getById(itemId).objectToJson();
    }

    @GetMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showCustomerByParamId(@RequestParam(required = false, name = "id") Integer itemId) {
    	if(itemId == null) {
    		return listAllCustomers();
    	}
    	return itemRepository.getById(itemId).objectToJson();
    }
    
    //POST MAPPING
    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCustomer(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Item i = (Item) new Item().jsonToObject(requestBody);
    	return itemRepository
    			.saveOne(i)
    			.objectToJson();
    }
    //PUT MAPPING
    @PutMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCustomer(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Item i = (Item) new Item().jsonToObject(requestBody);
    	if(itemRepository.checkIfExist(i.itemId)) {
    		return itemRepository.updateOne(i).objectToJson();
    	}
    	return itemRepository
    			.saveOne(i)
    			.objectToJson();
    }
    //DELETE MAPPINGS
    @DeleteMapping(value = "/item/{id}")
    public String deleteCustomerById(@PathVariable("id") Integer itemId) {
    	if(itemRepository.checkIfExist(itemId)) {
    		itemRepository.deleteOne(itemId);
    		return "success";
    	}
		return "fail";
    }
    @DeleteMapping(value = "/item")
    public String deleteCustomerByParamId(@RequestParam("id") Integer itemId) {
    	if(itemRepository.checkIfExist(itemId)) {
    		itemRepository.deleteOne(itemId);
    		return "success";
    	}
		return "fail";
    }
    //OPTIONS MAPPING
    @RequestMapping(value = "/item", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> preflight() {
    	System.out.println("Someone triggered this function.");
    	return new ResponseEntity<Void>( HttpStatus.OK );
    }
}