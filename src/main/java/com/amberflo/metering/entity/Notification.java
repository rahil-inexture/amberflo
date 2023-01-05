package com.amberflo.metering.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Notification {
	
	private String name;
	private String meterId;
	private String cron;
	private String description;
	private String webhookUrl;
	private String webhookHeaders;
	private String webhookPayload;
	private String email;
	private String thresholdCondition;
	private int thresholdValue;
	private String range;
	private String customerFilterMode;
	private String customerId;
	private boolean enabled;
}
