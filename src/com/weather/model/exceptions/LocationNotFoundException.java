package com.weather.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NO_CONTENT, reason="Location not found in Google Maps") 
public class LocationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2675490592867878989L;

	
}
