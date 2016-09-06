package com.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	@JsonProperty("lat")
	private double latitude;
	
	@JsonProperty("lng")
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double lat) {
		this.latitude = lat;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double lng) {
		this.longitude = lng;
	}
	
	
}
