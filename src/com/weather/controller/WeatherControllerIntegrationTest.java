package com.weather.controller;


import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.weather.model.ForecastResponse;
import com.weather.model.Geometry;
import com.weather.model.Location;
import com.weather.model.MapAPIResponse;
import com.weather.model.exceptions.ExternalServiceInvocationException;
import com.weather.service.ForecastRetriever;
import com.weather.service.MapInfoRetriever;


@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:/weatherControllerTest-servlet.xml"})
@PropertySource("classpath:/app-test.properties")
public class WeatherControllerIntegrationTest extends AbstractJUnit4SpringContextTests {

	private MockMvc mockMvc;
	
	@Autowired
	private ForecastRetriever forecastRetriever;
	
	@Autowired
	private MapInfoRetriever mapInfoRetriever;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		Mockito.reset(forecastRetriever);
		Mockito.reset(mapInfoRetriever);
	}
	
	private MediaType getMediaType() {
		return MediaType.APPLICATION_JSON;
	}
	
	@Test
	public void testGetForecastReturnsHTTP_OK_OnValidRequest() throws Exception {
		// mock service calls
		Mockito.when(mapInfoRetriever.getMapInfoFor("TestCity", "PA")).thenReturn(buildStubMapResponse());
		Mockito.when(forecastRetriever.getForcastFor("100.0", "100.0")).thenReturn(buildStubForecastResponse());

		// make GET request to endpoint
		mockMvc.perform(MockMvcRequestBuilders.get("/weather/forecast/TestCity,PA").accept(getMediaType()).contentType(getMediaType()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.longitude", Matchers.is(100)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.latitude", Matchers.is(100)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.offset", Matchers.is(200)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.formattedAddress", Matchers.is("city,township,state,zip")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.searchAddress", Matchers.is("TestCity,PA")));
//                .andDo(MockMvcResultHandlers.print());
		
        Mockito.verify(mapInfoRetriever, Mockito.times(1)).getMapInfoFor("TestCity", "PA");
        Mockito.verifyNoMoreInteractions(mapInfoRetriever);
        Mockito.verify(forecastRetriever, Mockito.times(1)).getForcastFor("100.0", "100.0");
        Mockito.verifyNoMoreInteractions(forecastRetriever);
	}

	@Test
	public void testGetForecastReturnsBAD_GATEWAY_OnGoogleServiceInvocationError() throws Exception {
		// mock service calls
		ExternalServiceInvocationException ex = new ExternalServiceInvocationException("Google", "failed");
		Mockito.when(mapInfoRetriever.getMapInfoFor("TestCity", "PA")).thenThrow(ex);

		// make GET request to endpoint
		mockMvc.perform(MockMvcRequestBuilders.get("/weather/forecast/TestCity,PA").accept(getMediaType()).contentType(getMediaType()))
                .andExpect(MockMvcResultMatchers.status().isBadGateway());
		
        Mockito.verify(mapInfoRetriever, Mockito.times(1)).getMapInfoFor("TestCity", "PA");
        Mockito.verifyNoMoreInteractions(mapInfoRetriever);
	}

	@Test
	public void testGetForecastReturnsBAD_GATEWAY_OnForecastIOServiceInvocationError() throws Exception {
		// mock service calls
		Mockito.when(mapInfoRetriever.getMapInfoFor("TestCity", "PA")).thenReturn(buildStubMapResponse());
		ExternalServiceInvocationException ex = new ExternalServiceInvocationException("FORECAST", "failed");
		Mockito.when(forecastRetriever.getForcastFor("100.0", "100.0")).thenThrow(ex);

		// make GET request to endpoint
		mockMvc.perform(MockMvcRequestBuilders.get("/weather/forecast/TestCity,PA").accept(getMediaType()).contentType(getMediaType()))
                .andExpect(MockMvcResultMatchers.status().isBadGateway());
		
        Mockito.verify(mapInfoRetriever, Mockito.times(1)).getMapInfoFor("TestCity", "PA");
        Mockito.verifyNoMoreInteractions(mapInfoRetriever);
        Mockito.verify(forecastRetriever, Mockito.times(1)).getForcastFor("100.0", "100.0");
        Mockito.verifyNoMoreInteractions(forecastRetriever);
	}
	
	@Test
	public void testBuildSearchAddressConcatenatesCityStateSearchArguments() {
		WeatherController controller = new WeatherController();
		Assert.assertEquals("Phila,PA", controller.buildSearchAddress("Phila", "PA"));
	}

	@Test
	public void testBuildSearchAddressReturnsCommaStringWithEmptyCityStateSearchArguments() {
		WeatherController controller = new WeatherController();
		Assert.assertEquals(",", controller.buildSearchAddress("", ""));
	}

	private ForecastResponse buildStubForecastResponse() {
		ForecastResponse stubForecastResponse = new ForecastResponse();
		stubForecastResponse.setLatitude(100);
		stubForecastResponse.setLongitude(100);
		stubForecastResponse.setOffset(200);
		return stubForecastResponse;
	}
	
	private MapAPIResponse buildStubMapResponse() {
		MapAPIResponse stubMapResponse = new MapAPIResponse();
		Geometry stubGeo = new Geometry();
		Location stubLocation = new Location();
		stubLocation.setLatitude(100);
		stubLocation.setLongitude(100);
		stubGeo.setLocation(stubLocation);
		stubMapResponse.setGeometry(stubGeo);
		stubMapResponse.setFormattedAddress("city,township,state,zip");
		return stubMapResponse;
	}

}
