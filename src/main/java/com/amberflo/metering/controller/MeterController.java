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

import com.amberflo.metering.entity.Meter;
import com.amberflo.metering.service.MeterService;
import com.amberflo.metering.utility.CommonHttpMethodUtility;
import com.amberflo.metering.utility.JParserUtil;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class MeterController {
	final static Logger logger = LoggerFactory.getLogger(CommonHttpMethodUtility.class);

	@Autowired
	private MeterService meterService;
	@Autowired
	private JParserUtil jParserUtil;  
	
	
	@PostMapping("/meter")
	public ResponseEntity<?> createMeter(@RequestBody Meter meter) throws IOException {
		String response = meterService.createMeter(meter);
		JsonNode jsonRes = jParserUtil.convertStringToJson(response);
		logger.info("create new meter ...");
		return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	}

	@GetMapping("/meter")
	public ResponseEntity<?> getMetersList() {
		try {
			String response = meterService.getAllMeters();
			JsonNode jsonRes = jParserUtil.convertStringToJson(response);
			logger.debug("get all meterlist ...");
			return new ResponseEntity<>(jsonRes, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Error :", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// get meter by ID
	@GetMapping("/meter/{meter_id}")
	public ResponseEntity<?> getMeterById(@PathVariable("meter_id")String meter_id) {
		try {
			String response = meterService.getMeterById(meter_id);
			JsonNode jsonRes = jParserUtil.convertStringToJson(response);
			logger.debug("get meter by ID ...");
			return new ResponseEntity<>(jsonRes, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Error :", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//edit meter by ID
	@PutMapping("/meter/{meterId}")
	public ResponseEntity<?> updateMeter(@PathVariable("meterId")String meterId, @RequestBody Meter meter) throws IOException {
		String response = meterService.editMeter(meterId, meter);
		JsonNode jsonRes = jParserUtil.convertStringToJson(response);
		return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	}
	
	//delete meter
	@DeleteMapping("/meter/{meter_id}")
	public ResponseEntity<?> deleteMeter(@PathVariable("meter_id")String meter_id) throws IOException {
		String response = meterService.deleteMeterById(meter_id);
		JsonNode jsonRes = jParserUtil.convertStringToJson(response);
		logger.info("remove meter by ID ...");
		return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	}
	
	// testing purpose call url
	@GetMapping("/hello")
	public ResponseEntity<?> getTest() {
			logger.debug("get all meterlist ...");
			return new ResponseEntity<>("Hello World", HttpStatus.OK);
	}
}