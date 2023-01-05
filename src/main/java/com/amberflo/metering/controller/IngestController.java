package com.amberflo.metering.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amberflo.metering.entity.IngestMeter;
import com.amberflo.metering.service.IngestService;
import com.amberflo.metering.utility.JParserUtil;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class IngestController {
	final static Logger logger = LoggerFactory.getLogger(IngestController.class);
	
	@Autowired
	private IngestService ingestService;
	@Autowired
	private JParserUtil jParserUtil;

	@Value("${amberflo.s3}")
	private boolean isS3Enabled;



	@PostMapping("/ingest")
	public ResponseEntity<?> createMeter(@RequestBody List<IngestMeter> ingestMeter) {
		try {
			if(isS3Enabled){
				ingestService.commonHttpMethodUtility.ingestMeterUsingS3Bucket(ingestMeter, ingestService);
				logger.info("Success Ingest meter using s3 Bucket ...");
				return new ResponseEntity<>("Successfully meter ingested", HttpStatus.CREATED);
			}else{
				String response = ingestService.createCustomerwiseIngestMeter(ingestMeter);
				JsonNode jsonRes = jParserUtil.convertStringToJson(response);
				logger.info("Success Ingest meter ...");

				return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
			}
	    } catch (Exception e) {
	    	logger.info("Error :", e);
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}