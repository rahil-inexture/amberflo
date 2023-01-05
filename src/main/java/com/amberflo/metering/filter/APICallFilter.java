package com.amberflo.metering.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.amberflo.metering.entity.IngestMeter;
import com.amberflo.metering.service.IngestService;

@WebFilter(urlPatterns = {"/deploy/*"})
public class APICallFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(APICallFilter.class);
	
	@Autowired
	private IngestService ingestService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        IngestMeter ingestMeter = new IngestMeter();
		ingestMeter.setCustomerId("Rail_001");
		ingestMeter.setMeterApiName("ESApiCalls");
		ingestMeter.setMeterTimeInMillis(System.currentTimeMillis());
		ingestMeter.setMeterValue(1);
			
		List<IngestMeter> ingest = new ArrayList<IngestMeter>();
		ingest.add(ingestMeter);
		
		chain.doFilter(request, response);
        if(res.getStatus() == HttpStatus.OK.value()) {
        	logger.info(String.format("Ingest response  is a %s ", ingestService.createCustomerwiseIngestMeter(ingest)));
        }else {
        	logger.error(String.format("Not Ingested because status code is %d ", res.getStatus()));
        }  
	}
}
