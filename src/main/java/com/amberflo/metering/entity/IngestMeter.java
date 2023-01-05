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
public class IngestMeter {
	
	private String customerId;
	private String meterApiName;
	private int meterValue;
	private long meterTimeInMillis;
	private String lockingStatus;
}