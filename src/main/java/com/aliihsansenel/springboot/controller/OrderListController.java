package com.aliihsansenel.springboot.controller;

import com.aliihsansenel.springboot.model.Customer;
import com.aliihsansenel.springboot.model.OrderListItem;
import com.aliihsansenel.springboot.repository.OrderListRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderListController {

    private OrderListRepository orderListRepository = new OrderListRepository();

    //GET MAPPINGS
    @GetMapping(value = "/orderlist", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listOrderList(){
    	return Customer.arrayToJson(orderListRepository.getAll());
    }
    //POST MAPPING
    @PostMapping(value = "/orderlist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCustomer(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	OrderListItem i = (OrderListItem) new OrderListItem().jsonToObject(requestBody);
    	return orderListRepository
    			.saveOne(i)
    			.objectToJson();
    }
    //DELETE MAPPINGS
    @DeleteMapping(value = "/orderlist/{id}")
    public String deleteOrderListItemById(@PathVariable("id") Integer orderId) {
    	if(orderListRepository.checkIfExist(orderId)) {
    		orderListRepository.deleteOne(orderId);
    		return "success";
    	}
		return "fail";
    }
    @DeleteMapping(value = "/orderlist")
    public String deleteCustomerByParamId(@RequestParam("id") Integer orderId) {
    	if(orderListRepository.checkIfExist(orderId)) {
    		orderListRepository.deleteOne(orderId);
    		return "success";
    	}
		return "fail";
    }
    //OPTIONS MAPPING
    @RequestMapping(value = "/orderlist", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> preflight() {
    	System.out.println("Someone triggered this function.");
    	return new ResponseEntity<Void>( HttpStatus.OK );
    }
}
