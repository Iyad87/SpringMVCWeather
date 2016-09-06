package com.weather.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.model.GoogleAPIResponse;
import com.weather.model.MapAPIResponse;

@Service
public class MapInfoRetrieverImpl implements MapInfoRetriever {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("#{myProps['map.api.key']}")
	private String mapApiKey;

	@Value("#{myProps['map.base.url']}")
	private String mapBaseUrl;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getMapApiKey() {
		return mapApiKey;
	}

	public void setMapApiKey(String mapApiKey) {
		this.mapApiKey = mapApiKey;
	}

	public String getMapBaseUrl() {
		return mapBaseUrl;
	}

	public void setMapBaseUrl(String mapBaseUrl) {
		this.mapBaseUrl = mapBaseUrl;
	}

	private Map<String,String> buildURLMap(String city, String state) {
		Map<String, String> arguments = new HashMap<String,String>();
		arguments.put("city", city);
		arguments.put("state", state);
		arguments.put("apiKey", getMapApiKey());
		return arguments;
	}
	
	@Override
	public MapAPIResponse getMapInfoFor(String city, String state) {
		GoogleAPIResponse rawResponse = restTemplate.getForObject(this.getMapBaseUrl(), 
				GoogleAPIResponse.class, buildURLMap(city, state));
		MapAPIResponse mapResponse = rawResponse.getResults().get(0);
		return mapResponse;
	}

}
