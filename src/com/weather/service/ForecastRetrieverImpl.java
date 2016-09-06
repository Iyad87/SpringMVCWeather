package com.weather.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.model.ForecastResponse;

@Service
public class ForecastRetrieverImpl implements ForecastRetriever {

	@Value("#{myProps['forecastio.api.key']}")
	private String forecastioApiKey;

	@Value("#{myProps['forecastio.base.url']}")
	private String forecastioBaseUrl;

	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getForecastioApiKey() {
		return forecastioApiKey;
	}
	
	public void setForecastioApiKey(String forecastioApiKey) {
		this.forecastioApiKey = forecastioApiKey;
	}

	public String getForecastioBaseUrl() {
		return forecastioBaseUrl;
	}

	public void setForecastioBaseUrl(String forecastioBaseUrl) {
		this.forecastioBaseUrl = forecastioBaseUrl;
	}

	Map<String,String> buildURLMap(String longitude, String latitude) {
		Map<String,String> arguments = new HashMap<String,String>();
		arguments.put("apiKey", getForecastioApiKey());
		arguments.put("longitude", longitude);
		arguments.put("latitude", latitude);
		return arguments;
	}
	
	@Override
	public ForecastResponse getForcastFor(String longitude, String latitude) {
		ForecastResponse forecast = restTemplate.getForObject(getForecastioBaseUrl(), 
				ForecastResponse.class, buildURLMap(longitude, latitude));
		return forecast;
	}

}
