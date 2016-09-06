package com.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {

	@JsonProperty("location_type")
	private String locationType;
	
	private Location location;
	
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String location_type) {
		this.locationType = location_type;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
}
