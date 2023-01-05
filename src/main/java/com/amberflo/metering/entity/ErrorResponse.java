package com.amberflo.metering.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ErrorResponse {
	  
	private int statusCode;
	private Date timestamp;
	private String message;
}
