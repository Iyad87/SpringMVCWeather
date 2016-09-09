package com.weather.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.StringStartsWith;
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
import com.weather.model.GoogleAPIResponse;
import com.weather.model.MapAPIResponse;
import com.weather.model.exceptions.ExternalServiceInvocationException;


@ContextConfiguration(locations = {"classpath:/applicationContext-test.xml"})
@PropertySource("classpath:/app.properties")
public class MapInfoRetrieverImplIntegrationTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MapInfoRetrieverImpl service;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	@Before
	public void setUp() throws Exception {
		mockServer = MockRestServiceServer.createServer(restTemplate);	
	}

	@Test
	public void testGetMapInfoForThrowsExternalServiceInvocationWhenGoogleResponseWithBadHTTPStatus() {
		mockServer.expect(MockRestRequestMatchers.anything())
        	.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
        	.andRespond(MockRestResponseCreators.withStatus(HttpStatus.BAD_GATEWAY));
		try {
			service.getMapInfoFor("Phila", "PA");
			fail();
		} catch (ExternalServiceInvocationException ex) {
			assertEquals(HttpStatus.BAD_GATEWAY.value(), ex.getExternalServiceHTTPStatusCode());
		}
		mockServer.verify();
	}

	@Test
	public void testGetMapInfoForReturnsMapAPIResponseObjectOnValidInvocation() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		GoogleAPIResponse stubResponse = stubGoogleResponseWithOneResult();
		String jsonString = mapper.writeValueAsString(stubResponse);
		mockServer.expect(MockRestRequestMatchers.anything())
        	.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
        	.andRespond(MockRestResponseCreators.withSuccess(jsonString, MediaType.APPLICATION_JSON));
		MapAPIResponse actualResponse = service.getMapInfoFor("Phila", "PA");
		assertNotNull(actualResponse);
		mockServer.verify();
	}

	@Test
	public void testPropertiesAreWiredCorrectly() {
		assertEquals("GOOGLE", service.getMapApiKey());
		assertThat(service.getMapBaseUrl(), StringStartsWith.startsWith("https://maps."));
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
