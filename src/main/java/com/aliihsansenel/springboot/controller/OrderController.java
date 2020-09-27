package com.aliihsansenel.springboot.controller;


import com.aliihsansenel.springboot.model.Order;
import com.aliihsansenel.springboot.repository.OrderRepository;

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
public class OrderController {

	private OrderRepository orderRepository = new OrderRepository();
	
	//GET MAPPINGS
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listAllOrders(){
    	return Order.arrayToJson(orderRepository.getAll());
    }
    @GetMapping(value = "/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showOrderById(@PathVariable("id") Integer orderId){
    	return orderRepository.getById(orderId).objectToJson();
    }

    @GetMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showOrderByParamId(@RequestParam(required = false, name = "id") Integer orderId) {
    	if(orderId == null) {
    		return listAllOrders();
    	}
    	return orderRepository.getById(orderId).objectToJson();
    }
    
    //POST MAPPING
    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveOrder(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Order o = (Order) new Order().jsonToObject(requestBody, false);
    	return orderRepository
    			.saveOne(o)
    			.objectToJson();
    }
    //PUT MAPPING
    @PutMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateOrder(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Order o = (Order) new Order().jsonToObject(requestBody, true);
    	if(orderRepository.checkIfExist(o.orderId)) {
    		return orderRepository.updateOne(o).objectToJson();
    	}
    	return orderRepository
    			.saveOne(o)
    			.objectToJson();
    }
    //DELETE MAPPINGS
    @DeleteMapping(value = "/order/{id}")
    public String deleteOrderById(@PathVariable("id") Integer orderId) {
    	if(orderRepository.checkIfExist(orderId)) {
    		orderRepository.deleteOne(orderId);
    		return "success";
    	}
		return "fail";
    }
    @DeleteMapping(value = "/order")
    public String deleteOrderByParamId(@RequestParam("id") Integer orderId) {
    	if(orderRepository.checkIfExist(orderId)) {
    		orderRepository.deleteOne(orderId);
    		return "success";
    	}
		return "fail";
    }
    //OPTIONS MAPPING
    @RequestMapping(value = "/order", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> preflight() {
    	System.out.println("Someone triggered this function.");
    	return new ResponseEntity<Void>( HttpStatus.OK );
    }
}
