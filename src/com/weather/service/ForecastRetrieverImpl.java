package com.weather.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.weather.model.ForecastResponse;
import com.weather.model.exceptions.ExternalServiceInvocationException;

@Service
public class ForecastRetrieverImpl implements ForecastRetriever {

	protected static final String ARG_LATITUDE = "latitude";
	protected static final String ARG_LONGITUDE = "longitude";
	protected static final String ARG_API_KEY = "apiKey";

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
		arguments.put(ARG_API_KEY, getForecastioApiKey());
		arguments.put(ARG_LONGITUDE, longitude);
		arguments.put(ARG_LATITUDE, latitude);
		return arguments;
	}
	
	@Override
	public ForecastResponse getForcastFor(String longitude, String latitude) {
		try {
		ForecastResponse forecast = restTemplate.getForObject(getForecastioBaseUrl(), 
				ForecastResponse.class, buildURLMap(longitude, latitude));
		return forecast;
		} catch (HttpStatusCodeException httpStatusEx) {
			// Forecast.IO only return HTTPStatus code (not error response) so catch exceptions here and convert to 
			// our common Exception for easier error handling
			System.out.println("HttpStatus from ForecastIO is: " + httpStatusEx.getRawStatusCode());
			throw new ExternalServiceInvocationException("ForecastIOException", httpStatusEx.getRawStatusCode());
		}
	}

}
