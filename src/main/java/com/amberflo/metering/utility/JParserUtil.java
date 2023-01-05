package com.amberflo.metering.utility;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JParserUtil {

	public JsonNode convertStringToJson(String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		
		try {
			actualObj = mapper.readTree(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	   
		return actualObj;
	}
}
