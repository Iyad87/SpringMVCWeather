package com.weather.service;

import static org.junit.Assert.*;
import org.hamcrest.core.*;
import org.junit.Before;
import org.junit.Test;
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
		assertEquals("FORECAST", service.getDarkskyApiKey());
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
