package com.weather.service;

import com.weather.model.MapAPIResponse;

public interface MapInfoRetriever {

	public MapAPIResponse getMapInfoFor(String city, String state);
	
}
