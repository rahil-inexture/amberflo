package com.amberflo.metering.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
	
@RestController
public class DeploymentController {
	final static Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	
	

	@PostMapping("/deploy/call-deployment-api")
	public ResponseEntity<?> createCustomer() {
		try {
			
			return new ResponseEntity<>(null, HttpStatus.OK);	    
	    } catch (Exception e) {
	    	logger.info("Error :", e);
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
