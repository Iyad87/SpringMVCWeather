package com.weather.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;
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
	public void testGetMapInfoForThrowsExternalServiceInvocationOnBadHTTPStatus() {
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


	
}
