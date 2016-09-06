package com.weather.model;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	    @Override
	    public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
	    	long timeAsLong = arg0.getLongValue();
	    	LocalDateTime localDateTime =
	    		    LocalDateTime.ofInstant(Instant.ofEpochSecond(timeAsLong), ZoneId.systemDefault());
//	    	System.out.println("Time is: " + localDateTime.getHour() + " Min: " + localDateTime.getMinute() + " Sec: " + localDateTime.getSecond());
	        return localDateTime;
	    }
}
