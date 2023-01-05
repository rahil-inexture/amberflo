package com.amberflo.metering.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Meter {
	
	private String id;
	private String label;
	private String meterApiName;
	private String meterType; //sum_of_all_usage, max_usage, running_total, event_duration, unique_count
	private String description;
	private String lockingStatus; // open, close_to_changes, close_to_deletion, deprecated.
	private ArrayList<String> dimensions;		
}