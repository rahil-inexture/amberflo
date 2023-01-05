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

import com.amberflo.metering.entity.Notification;
import com.amberflo.metering.service.NotificationService;
import com.amberflo.metering.utility.JParserUtil;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class NotificationController {
	final static Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private JParserUtil jParserUtil;
	

	@PostMapping("/notifications")
	public ResponseEntity<?> createNotification(@RequestBody Notification notification) {
		try {
	    	String response = notificationService.createNotification(notification);
	    	JsonNode jsonRes = jParserUtil.convertStringToJson(response);
	    	logger.info("create new signal ...");
	    	
	    	return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	    } catch (Exception e) {
	    	logger.info("Error :", e);
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/notifications")
	public ResponseEntity<?> getNotificationsList() {
		try {
			String response = notificationService.getAllNotifications();
			JsonNode jsonRes = jParserUtil.convertStringToJson(response);
			logger.debug("get all Notification list ...");
			return new ResponseEntity<>(jsonRes, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Error :", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// get by id
	@GetMapping("/notifications/{notification_id}")
	public ResponseEntity<?> getNotificationById(@PathVariable("notification_id")String notification_id) {
		try {
			String response = notificationService.getNotificationById(notification_id);
			JsonNode jsonRes = jParserUtil.convertStringToJson(response);
			logger.debug("get notification by id ...");
			return new ResponseEntity<>(jsonRes, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("Error :", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//edit notification by id
	@PutMapping("/notifications/{notification_id}")
	public ResponseEntity<?> updateNotification(@PathVariable("notification_id")String notification_id, @RequestBody Notification notification) throws IOException {
		String response = notificationService.editNotification(notification_id, notification);
		JsonNode jsonRes = jParserUtil.convertStringToJson(response);
		return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	}
	
	//delete notification
	@DeleteMapping("/notifications/{notification_id}")
	public ResponseEntity<?> deleteNotification(@PathVariable("notification_id")String notification_id) throws IOException {
		String response = notificationService.deleteNotificationById(notification_id);
		JsonNode jsonRes = jParserUtil.convertStringToJson(response);
		logger.info("delete notification ...");
		return new ResponseEntity<>(jsonRes, HttpStatus.CREATED);
	}
}