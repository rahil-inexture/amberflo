package com.amberflo.metering.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amberflo.metering.entity.Customer;
import com.amberflo.metering.service.CustomerService;
import com.amberflo.metering.utility.JParserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
	
@RestController
public class CustomerController {
	final static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private JParserUtil jParserUtil;
	

	@PostMapping("/customers")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
		try {
	    	String response  = customerService.createNewCustomer(customer);
	    	JsonNode jsonRes = jParserUtil.convertStringToJson(response);
	    	logger.info("Success createed customer  ...");
	    	
	    	return new ResponseEntity<>(jsonRes, HttpStatus.OK);	    
	    } catch (Exception e) {
	    	logger.info("Error :", e);
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@GetMapping("/customers")
	public ResponseEntity<JsonNode> getCustomersList() {
		try {
			String response = customerService.getAllCustomers();
			JsonNode jsonRes = jParserUtil.convertStringToJson(response);
			logger.debug("get Customers List ...");
			return new ResponseEntity<JsonNode>(jsonRes, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Error :", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// get customer by ID
	@GetMapping("/customers/{customer_id}")
	public ResponseEntity<?> getCustomerById(@PathVariable("customer_id")String customer_id) {
		try {
			String response = customerService.getCustomerById(customer_id);
			JsonNode jsonRes = jParserUtil.convertStringToJson(response);
			logger.debug("get customer By ID ...");
			return new ResponseEntity<>(jsonRes, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Error :", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//edit customer by ID
	@PutMapping("/customers/{customer_id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("customer_id")String customer_id, @RequestBody Customer customer) throws IOException {
		String response = customerService.editCustomer(customer_id, customer);
		JsonNode jsonRes = jParserUtil.convertStringToJson(response);
		logger.debug("Edit customer By ID ...");
		return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	}
	
	//delete customer
	@DeleteMapping("/customers/{customer_id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("customer_id")String customer_id) throws IOException {
		String response = customerService.deleteCustomerById(customer_id);
		JsonNode jsonRes = jParserUtil.convertStringToJson(response);
		logger.info("delete customer ID", customer_id);
		return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	}
}
