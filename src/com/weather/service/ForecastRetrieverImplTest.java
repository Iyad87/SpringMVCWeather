package com.weather.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import com.weather.model.ForecastResponse;

public class ForecastRetrieverImplTest {

	private ForecastRetrieverImpl service;
	
	@Before
	public void setUp() throws Exception {
		service = new ForecastRetrieverImpl();
		service.setDarkskyBaseUrl("http://thebaseUrl/");
		service.setDarkskyApiKey("THE_KEY");
	}

	@Test
	public void testBuildURLMapReturnsProperlyPopulatedMapWithLongitudeAndLatitudeAndAPIKey() {
		Map<String,String> urlMap = service.buildURLMap("100", "200");
		assertEquals(urlMap.get(ForecastRetrieverImpl.ARG_LATITUDE), "200");
		assertEquals(urlMap.get(ForecastRetrieverImpl.ARG_LONGITUDE), "100");
		assertEquals(urlMap.get(ForecastRetrieverImpl.ARG_API_KEY), "THE_KEY");
		assertEquals(3, urlMap.keySet().size());
	}

	@Test
	public void testBuildURLMapReturnsMapWithDefaultAPIKey() {
		Map<String,String> urlMap = service.buildURLMap("", "");
		assertEquals(urlMap.get(ForecastRetrieverImpl.ARG_LATITUDE), "");
		assertEquals(urlMap.get(ForecastRetrieverImpl.ARG_LONGITUDE), "");
		assertEquals(urlMap.get(ForecastRetrieverImpl.ARG_API_KEY), "THE_KEY");
		assertEquals(3, urlMap.keySet().size());
	}
	
	@Test
	public void testGetForcastForInvokesRESTTemplateWithProperParameters() {
		RestTemplate mockREST = Mockito.mock(RestTemplate.class);
		Map<String,String> urlArgs = service.buildURLMap("100", "200");
		when(mockREST.getForObject("http://thebaseUrl/",ForecastResponse.class, urlArgs))
			.thenReturn(new ForecastResponse());
		service.setRestTemplate(mockREST);
		ForecastResponse actualResponse = service.getForcastFor("100", "200");
		assertNotNull(actualResponse);
	}
	
}
