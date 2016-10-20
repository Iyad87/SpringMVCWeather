package com.weather.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.GATEWAY_TIMEOUT, reason="Service invocation failed") 
public class ExternalServiceGatewayException extends RuntimeException {

	private static final long serialVersionUID = 1728342245949699094L;

	private String serviceName;
	private Exception innerException;
	
	public ExternalServiceGatewayException(String serviceName, Exception innerException) {
		this.serviceName = serviceName;
		this.innerException = innerException;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Exception getInnerException() {
		return innerException;
	}
	public void setInnerException(Exception innerException) {
		this.innerException = innerException;
	}
	
}
