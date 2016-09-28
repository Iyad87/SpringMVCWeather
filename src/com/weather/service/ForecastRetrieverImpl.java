package com.weather.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

	@Autowired
	private Environment environment;
	
//	@Value("#{systemEnvironment['DARKSKY_API_KEY']}")
//	@Value("#{myProps['darksky.api.key']}")
	private String darkskyApiKey;

	@Value("#{myProps['darksky.base.url']}")
	private String darkskyBaseUrl;

	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getDarkskyApiKey() {
		if (darkskyApiKey == null) {
			this.darkskyApiKey = environment.getProperty("DARKSKY_API_KEY");
		}
		return darkskyApiKey;
	}
	
	public void setDarkskyApiKey(String forecastioApiKey) {
		this.darkskyApiKey = forecastioApiKey;
	}

	public String getDarkskyBaseUrl() {
		return darkskyBaseUrl;
	}

	public void setDarkskyBaseUrl(String forecastioBaseUrl) {
		this.darkskyBaseUrl = forecastioBaseUrl;
	}

	Map<String,String> buildURLMap(String longitude, String latitude) {
		Map<String,String> arguments = new HashMap<String,String>();
		arguments.put(ARG_API_KEY, getDarkskyApiKey());
		arguments.put(ARG_LONGITUDE, longitude);
		arguments.put(ARG_LATITUDE, latitude);
		System.err.println("Hello, logs!");
		System.out.println("**** DarkskyApiKey out is: " + getDarkskyApiKey());
		System.err.println("**** DarkskyApiKey err is: " + getDarkskyApiKey());
		return arguments;
	}
	
	@Override
	public ForecastResponse getForcastFor(String longitude, String latitude) {
		try {
		ForecastResponse forecast = restTemplate.getForObject(getDarkskyBaseUrl(), 
				ForecastResponse.class, buildURLMap(longitude, latitude));
		return forecast;
		} catch (HttpStatusCodeException httpStatusEx) {
			// Darksky only return HTTPStatus code (not error response) so catch exceptions here and convert to 
			// our common Exception for easier error handling
//			System.out.println("HttpStatus from ForecastIO is: " + httpStatusEx.getRawStatusCode());
			throw new ExternalServiceInvocationException("ForecastIOException", httpStatusEx.getRawStatusCode());
		}
	}

}
