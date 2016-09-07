package com.weather.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import com.weather.model.GoogleAPIResponse;
import com.weather.model.MapAPIResponse;
import com.weather.model.exceptions.ExternalServiceInvocationException;
import com.weather.model.exceptions.LocationNotFoundException;


public class MapInfoRetrieverImplTest {

	private MapInfoRetrieverImpl service;
	
	@Before
	public void setUp() throws Exception {
		service = new MapInfoRetrieverImpl();
		service.setMapApiKey("API_KEY_VALUE");
		service.setMapBaseUrl("http://baseurl");
	}

	@Test
	public void testBuildURLMapReturnsMapPopulatedWithCityStateAndAPIKey() {
		Map<String,String> argMap = service.buildURLMap("Phila", "PA");
		assertEquals(3, argMap.keySet().size());
		assertEquals("Phila", argMap.get(MapInfoRetrieverImpl.ARG_CITY));
		assertEquals("PA", argMap.get(MapInfoRetrieverImpl.ARG_STATE));
		assertEquals("API_KEY_VALUE", argMap.get(MapInfoRetrieverImpl.ARG_API_KEY));
	}

	@Test
	public void testGetMapInfoForInvokesRESTTemplateWithProperArgumentsAndReturnsFirstAPIResponseObject() {
		RestTemplate mockREST = Mockito.mock(RestTemplate.class);
		Map<String,String> urlArgs = service.buildURLMap("Phila", "PA");
		when(mockREST.getForObject("http://baseurl",GoogleAPIResponse.class, urlArgs))
			.thenReturn(stubGoogleResponseWithOneResult());
		service.setRestTemplate(mockREST);
		MapAPIResponse actualResponse = service.getMapInfoFor("Phila", "PA");
		assertNotNull(actualResponse);
	}
	
	@Test
	public void testProcessGoogleResultsReturnsFirstItemInResultsWhenOKStatusReturned() {
		MapAPIResponse response = service.processGoogleResults(stubGoogleResponseWithOneResult());
		assertNotNull(response);
	}
	
	@Test(expected = LocationNotFoundException.class)
	public void testProcessGoogleResultsThrowsLocationNotFoundExceptionWhenServiceReturnsZERO_RESULTS() {
		GoogleAPIResponse stubResponse = new GoogleAPIResponse();
		stubResponse.setStatus(MapInfoRetrieverImpl.GOOGLE_RESPONSE_STATUS_ZERO_RESULTS);
		service.processGoogleResults(stubResponse);
	}

	@Test
	public void testProcessGoogleResultsThrowsExternalServiceInvocationExceptionWithRootStatusOnOtherGoogleStatusCodes() {
		GoogleAPIResponse stubResponse = new GoogleAPIResponse();
		stubResponse.setStatus("OVER_QUERY_LIMIT");
		try {
			service.processGoogleResults(stubResponse);
			fail();
		} catch (ExternalServiceInvocationException ex) {
			assertEquals("OVER_QUERY_LIMIT", ex.getExternalResultStatusCode());
			assertEquals(MapInfoRetrieverImpl.GOOGLE_MAP_GEOCODE_SERVICE, ex.getServiceName());
		}
	}

	private GoogleAPIResponse stubGoogleResponseWithOneResult() {
		GoogleAPIResponse stubResponse = new GoogleAPIResponse();
		List<MapAPIResponse> mapResponseList = new ArrayList<MapAPIResponse>();
		mapResponseList.add(new MapAPIResponse());
		stubResponse.setStatus(MapInfoRetrieverImpl.GOOGLE_RESPONSE_STATUS_OK);
		stubResponse.setResults(mapResponseList);
		return stubResponse;
	}
	
}
