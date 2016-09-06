package com.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleAPIResponse {

	private String status;
	private List<MapAPIResponse> results;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<MapAPIResponse> getResults() {
		return results;
	}
	public void setResults(List<MapAPIResponse> results) {
		this.results = results;
	}
	

}
