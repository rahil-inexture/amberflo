package com.amberflo.metering.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amberflo.metering.entity.IngestMeter;
import com.amberflo.metering.utility.CommonHttpMethodUtility;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class IngestService {
	final static Logger logger = LoggerFactory.getLogger(IngestService.class);
	@Autowired
	public CommonHttpMethodUtility commonHttpMethodUtility;

	@Value("${amberflo.ingest.post_method}")
	public String INGEST_POST_URL;
	@Value("${amberflo.s3.access.key}")
	public String ACCESS_KEY;
	@Value("${amberflo.s3.secret.key}")
	public String SECRET_KEY;
	@Value("${amberflo.s3.bucket.name}")
	public String BUCKET_NAME;

	public String createCustomerwiseIngestMeter(List<IngestMeter> ingestMeter) throws IOException {
		String entityStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(ingestMeter); 
		String response = commonHttpMethodUtility.amberfloHttpPostMethod(entityStr, INGEST_POST_URL);
		return response;
	}

}