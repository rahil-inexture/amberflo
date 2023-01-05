package com.amberflo.metering.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amberflo.metering.entity.Notification;
import com.amberflo.metering.utility.CommonHttpMethodUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationService {
	
	@Autowired
	private CommonHttpMethodUtility commonHttpMethodUtility;
	
	@Value("${amberflo.signal.post_method}")
	private String SIGNAL_POSTURL;
	@Value("${amberflo.signal.pgd_method}")
	private String SIGNAL_PGDURL;
	
	
	public String createNotification(Notification notification) throws IOException {
		String entityStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(notification); 
		String response = commonHttpMethodUtility.amberfloHttpPostMethod(entityStr, SIGNAL_POSTURL);
		
		return response;	
	}
	
	//get all notifications
	public String getAllNotifications() throws IOException {
		String response = commonHttpMethodUtility.amberfloHttpGetMethod(SIGNAL_POSTURL);
		return response;
	}

	//edit notification
	public String editNotification(String notificationId, Notification notification) throws IOException {
		String entityStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(notification);
		return commonHttpMethodUtility.amberfloHttpPutMethod(entityStr, SIGNAL_PGDURL, notificationId);
	}

	//get notification by id
	public String getNotificationById(String notificationId) throws IOException {
		return commonHttpMethodUtility.amberfloHttpGetMethodById(notificationId, SIGNAL_PGDURL);
	}
	
	//delete notification by id
	public String deleteNotificationById(String notificationId) throws IOException {
		return commonHttpMethodUtility.amberfloHttpGetMethodById(notificationId, SIGNAL_PGDURL);
	}
}