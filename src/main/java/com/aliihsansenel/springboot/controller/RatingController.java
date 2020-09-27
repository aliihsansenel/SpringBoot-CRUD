package com.aliihsansenel.springboot.controller;


import com.aliihsansenel.springboot.model.Customer;
import com.aliihsansenel.springboot.model.Rating;
import com.aliihsansenel.springboot.model.RatingsJoined;
import com.aliihsansenel.springboot.repository.RatingRepository;

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
public class RatingController {

    private RatingRepository ratingRepository = new RatingRepository();

    //GET MAPPINGS
    @GetMapping(value = "/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listAllRatings(){
    	return Customer.arrayToJson(ratingRepository.getAll());
    }
    @GetMapping(value = "/ratingsJoined", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listAllRatingsJoined(){
    	return RatingsJoined.arrayToJson(ratingRepository.getAllJoined());
    }
    @GetMapping(value = "/rating/{id1}/{id2}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showRatingById(@PathVariable("id1") Integer customerId, @PathVariable("id2") Integer itemId){
    	return ratingRepository.getById(customerId, itemId).objectToJson();
    }

    @GetMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showRatingByParamId(@RequestParam(required = false, name = "id1") Integer customerId, @RequestParam(required = false, name = "id2") Integer itemId) {
    	if(customerId == null || itemId == null) {
    		return listAllRatings();
    	}
    	return ratingRepository.getById(customerId , itemId).objectToJson();
    }
    
    //POST MAPPING
    @PostMapping(value = "/rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveRating(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Rating r = (Rating) new Rating().jsonToObject(requestBody);
    	return ratingRepository
    			.saveOne(r)
    			.objectToJson();
    }
    //PUT MAPPING
    @PutMapping(value = "/rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateRating(@RequestBody String requestBody) {
    	System.out.println(requestBody);
    	Rating r = (Rating) new Rating().jsonToObject(requestBody);
    	if(ratingRepository.checkIfExist(r.customerId, r.itemId)) {
    		return ratingRepository.updateOne(r).objectToJson();
    	}
    	return ratingRepository
    			.saveOne(r)
    			.objectToJson();
    }
    //DELETE MAPPINGS
    @DeleteMapping(value = "/rating/{id1}/{id2}")
    public String deleteRatingById(@PathVariable("id1") Integer customerId, @PathVariable("id2") Integer itemId) {
    	if(ratingRepository.checkIfExist(customerId, itemId)) {
    		ratingRepository.deleteOne(customerId, itemId);
    		return "success";
    	}
		return "fail";
    }
    @DeleteMapping(value = "/rating")
    public String deleteRatingByParamId(@RequestParam("id1") Integer customerId, @RequestParam("id2") Integer itemId) {
    	if(ratingRepository.checkIfExist(customerId, itemId)) {
    		ratingRepository.deleteOne(customerId, itemId);
    		return "success";
    	}
		return "fail";
    }
    //OPTIONS MAPPING
    @RequestMapping(value = "/rating", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> preflight() {
    	System.out.println("Someone triggered this function.");
    	return new ResponseEntity<Void>( HttpStatus.OK );
    }
}
