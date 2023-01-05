package com.amberflo.metering.utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.amberflo.metering.entity.IngestMeter;
import com.amberflo.metering.ingest.MeteringContext;
import com.amberflo.metering.ingest.meter_message.MeterMessage;
import com.amberflo.metering.ingest.meter_message.MeterMessageBuilder;
import com.amberflo.metering.service.IngestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.amberflo.metering.ingest.MeteringContext.metering;

@Component
public class CommonHttpMethodUtility {
	final static Logger logger = LoggerFactory.getLogger(CommonHttpMethodUtility.class);		
	String resStr = null;
	
	@Value("${amberflo.apiKey}")
	private String AMBERFLO_API_KEY;	
	
	//post method 
	public String amberfloHttpPostMethod(String entityStr, String POST_URL) throws IOException {
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(entityStr.toString(), MediaType.parse("application/json"));
		Response response;
		String resStr = null;
		
		Request request = new Request.Builder()
				  .url(POST_URL)
				  .post(body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("x-api-key", AMBERFLO_API_KEY)
				  .build();
				
		response = client.newCall(request).execute();
		//commonExceptionHandle(response);
		resStr = response.body().string();
		return resStr;
	}
	
	// get method
	public String amberfloHttpGetMethod(String GET_URL) throws IOException {
		OkHttpClient client = new OkHttpClient();
		resStr = null;
		
		Request request = new Request.Builder()
		  .url(GET_URL)
		  .get()
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("x-api-key", AMBERFLO_API_KEY)
		  .build();

		Response response = client.newCall(request).execute();
		resStr = response.body().string();

		return resStr;
	}
		
	//update method
	public String amberfloHttpPutMethod(String entityStr, String PUT_URL, String meterId) throws IOException {
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(entityStr.toString(), MediaType.parse("application/json"));
		Response response;
		resStr = null;
		
		Request request = new Request.Builder()
				  .url(PUT_URL + meterId)
				  .put(body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("x-api-key", AMBERFLO_API_KEY)
				  .build();
		
		response = client.newCall(request).execute();
		resStr = response.body().string();
		logger.debug(resStr);
		
		return resStr;
	}
	
	// get method
	public String amberfloHttpGetMethodById(String meterId, String GET_URL) throws IOException {
		OkHttpClient client = new OkHttpClient();
		resStr = null;
		
		Request request = new Request.Builder()
		  .url(GET_URL + meterId)
		  .get()
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("x-api-key", AMBERFLO_API_KEY)
		  .build();

		Response response = client.newCall(request).execute();
		logger.debug(response.toString());
		resStr = response.body().string();
		return resStr;
	}
	
	//delete meter
	public String amberfloHttpDeleteMethod(String meterId, String DELETE_URL) throws IOException {
		OkHttpClient client = new OkHttpClient();
		resStr = null;
		
		Request request = new Request.Builder()
		  .url(DELETE_URL + meterId)
		  .delete(null)
		  .addHeader("Accept", "application/json")
		  .addHeader("x-api-key", AMBERFLO_API_KEY)
		  .build();

		Response response = client.newCall(request).execute();
		resStr = response.body().string();
		return resStr;
	}

	public void ingestMeterUsingS3Bucket(List<IngestMeter> ingestMeter, IngestService ingestService) {
		try {
				MeteringContext context = MeteringContext.contextWithS3Client(ingestService.BUCKET_NAME, ingestService.ACCESS_KEY, ingestService.SECRET_KEY, 5, 30);
				for (IngestMeter ingest: ingestMeter) {
					final MeterMessage meter = MeterMessageBuilder
							.createInstance(ingest.getMeterApiName(), LocalDateTime.now(ZoneOffset.UTC), ingest.getCustomerId())
							.setMeterValue(ingest.getMeterValue())
							.build();
					metering().meter(meter);
					logger.info("Meter Name: "+ ingest.getMeterApiName() + "Response: " + meter);
				}
		}catch (Exception e){
			logger.error("Error: " + e.getMessage());
		}
	}
}