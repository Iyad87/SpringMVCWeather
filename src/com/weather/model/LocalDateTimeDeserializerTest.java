package com.weather.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializerTest {

	LocalDateTimeDeserializer deserializer;
	
	@Before
	public void setUp() throws Exception {
		deserializer = new LocalDateTimeDeserializer();
	}

	@Test
	public void testDeserializeLocalDateTimeAsLongReturnsCorrectLocalDateTime() throws IOException {
		JsonParser parserArgument = mock(JsonParser.class);
		// 9/6/2016 2:53:14 PM
		long timeSinceEpoch = 1473187994l;
		when(parserArgument.getLongValue()).thenReturn(timeSinceEpoch);
		LocalDateTime actualDateTime = deserializer.deserialize(parserArgument, null);
		LocalDateTime expectedDateTime = LocalDateTime.of(2016, 9,6,14,53,14);
		assertEquals(expectedDateTime, actualDateTime);
	}

	@Test
	public void testDeserializeLocalDateTimeAsLongReturnsDatePriorToEpochForNegativeValues() throws IOException {
		JsonParser parserArgument = mock(JsonParser.class);
		// 12/31/1969 6:59:57 PM
		long timeSinceEpoch = -3l;
		when(parserArgument.getLongValue()).thenReturn(timeSinceEpoch);
		LocalDateTime actualDateTime = deserializer.deserialize(parserArgument, null);
		LocalDateTime expectedDateTime = LocalDateTime.of(1969, 12,31,18,59,57);
		assertEquals(expectedDateTime, actualDateTime);
	}

}
