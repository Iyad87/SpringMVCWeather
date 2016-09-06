package com.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MapAPIResponse {

	@JsonProperty("formatted_address")
	private String formattedAddress;
	
	@JsonProperty("place_id")
	private String placeId;
	
	private Geometry geometry;
	
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formatted_address) {
		this.formattedAddress = formatted_address;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String place_id) {
		this.placeId = place_id;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	

}
