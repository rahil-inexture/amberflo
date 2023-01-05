package com.amberflo.metering.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amberflo.metering.entity.Customer;
import com.amberflo.metering.utility.CommonHttpMethodUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerService {
	
	@Autowired
	private CommonHttpMethodUtility commonHttpMethodUtility;
	
	//get property
	@Value("${amberflo.customer.post_method}")
	private String PG_URL;
	//put property
	@Value("${amberflo.customer.gud_method}")
	private String PG_BYID_CUSTOMER_URL;
	
	// create new customer
	public String createNewCustomer(Customer customer) throws IOException {
		String entityStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(customer); 
		String response = commonHttpMethodUtility.amberfloHttpPostMethod(entityStr, PG_URL);
		return response;
	}
	
	//get all customers
	public String getAllCustomers() throws IOException {
		String response = commonHttpMethodUtility.amberfloHttpGetMethod(PG_URL);
		return response;
	}

	//edit customers
	public String editCustomer(String customerId, Customer customer) throws IOException {
		String entityStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(customer);
		return commonHttpMethodUtility.amberfloHttpPutMethod(entityStr, PG_BYID_CUSTOMER_URL, customerId);
	}

	//get customer by ID
	public String getCustomerById(String customerId) throws IOException {
		return commonHttpMethodUtility.amberfloHttpGetMethodById(customerId, PG_BYID_CUSTOMER_URL);
	}
	
	//delete Customer by ID
	public String deleteCustomerById(String customerId) throws IOException {
		return commonHttpMethodUtility.amberfloHttpDeleteMethod(customerId, PG_BYID_CUSTOMER_URL);
	}	
}