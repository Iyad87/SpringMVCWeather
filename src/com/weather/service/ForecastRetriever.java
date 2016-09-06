package com.weather.service;

import com.weather.model.ForecastResponse;

public interface ForecastRetriever {

	public ForecastResponse getForcastFor(String longitude, String latitude);
	
}
