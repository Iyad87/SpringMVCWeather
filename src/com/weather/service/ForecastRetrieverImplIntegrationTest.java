package com.weather.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.model.ForecastResponse;
import com.weather.model.GoogleAPIResponse;
import com.weather.model.exceptions.ExternalServiceGatewayException;
import com.weather.model.exceptions.ExternalServiceInvocationException;


@ContextConfiguration(locations = {"classpath:/applicationContext-test.xml"})
@PropertySource("classpath:/app-test.properties")
public class ForecastRetrieverImplIntegrationTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ForecastRetrieverImpl service;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	@Before
	public void setUp() throws Exception {
		mockServer = MockRestServiceServer.createServer(restTemplate);	
	}

	@Test
	public void testGetForcastForThrowsExternalServiceInvocationWhenForcastIORespondsWithBadHTTPStatus() {
		mockServer.expect(MockRestRequestMatchers.anything())
        	.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
        	.andRespond(MockRestResponseCreators.withStatus(HttpStatus.BAD_REQUEST));
		try {
			service.getForcastFor("100", "-100");
			fail();
		} catch (ExternalServiceInvocationException ex) {
			assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getExternalServiceHTTPStatusCode());
		}
		mockServer.verify();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetForcastForThrowsExternalServiceGatewayExceptionWhenGenericExceptionThrownInvokingService() {
		RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
		Map<String, String> arguments = new HashMap<String,String>();
		when(mockRestTemplate.getForObject(service.getDarkskyBaseUrl(), ForecastResponse.class, arguments))
		.thenThrow(Exception.class);
		ForecastRetrieverImpl serviceThrowingException = new ForecastRetrieverImpl();
		try {
			serviceThrowingException.getForcastFor("100", "-100");
			fail();
		} catch (ExternalServiceGatewayException ex) {
			assertEquals(ForecastRetrieverImpl.FORECAST_IO_SERVICE_NAME, ex.getServiceName());
			assertNotNull(ex.getInnerException());
		}
	}

	@Test
	public void testGetForcastForReturnsForcastResponseObjectOnValidInvocation() throws JsonProcessingException {
		String jsonResponse = buildDummyJSONResponse();
		mockServer.expect(MockRestRequestMatchers.anything())
    		.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
    		.andRespond(MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON));
		ForecastResponse actualResponse = service.getForcastFor("100", "-200");
		assertNotNull(actualResponse);
		assertEquals(-200, actualResponse.getLatitude());
		assertEquals(100, actualResponse.getLongitude());
		mockServer.verify();
	}

	@Test
	public void testPropertiesAreWiredCorrectly() {
		assertThat(service.getDarkskyBaseUrl(), StringStartsWith.startsWith("https://api.darksky.net"));
	}

	private String buildDummyJSONResponse() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ForecastResponse stubResponse = new ForecastResponse();
		stubResponse.setLatitude(-200);
		stubResponse.setLongitude(100);
		String jsonString = mapper.writeValueAsString(stubResponse);
		return jsonString;
	}
	
}
