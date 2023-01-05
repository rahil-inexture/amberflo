package com.amberflo.metering.service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amberflo.metering.entity.Meter;
import com.amberflo.metering.utility.CommonHttpMethodUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MeterService {

	@Autowired
	private CommonHttpMethodUtility commonHttpMethodUtility;
	
	
	//get property
	@Value("${amberflo.meter.pg_method}")
	private String POST_AND_GET_ALL_METER_URL;
	//put property
	@Value("${amberflo.meter.put_method}")
	private String PUT_METER_URL;
	//get property
	@Value("${amberflo.meter.get_method}")
	private String GET_METER_URL;
	
	
	//create meter
	public String createMeter(Meter meter) throws IOException {
		String entityStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(meter); 
		return commonHttpMethodUtility.amberfloHttpPostMethod(entityStr, POST_AND_GET_ALL_METER_URL);
	}
	
	//get all meters
	public String getAllMeters() throws IOException {
		String response = commonHttpMethodUtility.amberfloHttpGetMethod(POST_AND_GET_ALL_METER_URL);
		return response;
	}

	//edit meter
	public String editMeter(String meterId,Meter meter) throws IOException {
		String entityStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(meter);
		return commonHttpMethodUtility.amberfloHttpPutMethod(entityStr, PUT_METER_URL, meterId);
	}

	//get meter by id
	public String getMeterById(String meterId) throws IOException {
		return commonHttpMethodUtility.amberfloHttpGetMethodById(meterId, GET_METER_URL);
	}
	
	//delete meter by id
	public String deleteMeterById(String meterId) throws IOException {
		return commonHttpMethodUtility.amberfloHttpGetMethodById(meterId, GET_METER_URL);
	}
}