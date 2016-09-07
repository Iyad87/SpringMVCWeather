package com.weather.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_GATEWAY, reason="Service invocation failed") 
public class ExternalServiceInvocationException extends RuntimeException {

	private String serviceName;
	private int externalServiceHTTPStatusCode;
	private String externalResultStatusCode;
	
	private static final long serialVersionUID = 2675490592867878989L;

	public ExternalServiceInvocationException(String serviceName, String resultStatusCode) {
		this.serviceName = serviceName;
		this.externalResultStatusCode =resultStatusCode;
	}

	public ExternalServiceInvocationException(String serviceName, int externalHTTPStatusCode) {
		this.serviceName = serviceName;
		this.externalServiceHTTPStatusCode = externalHTTPStatusCode;
	}

	public ExternalServiceInvocationException(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getExternalServiceHTTPStatusCode() {
		return externalServiceHTTPStatusCode;
	}

	public void setExternalServiceHTTPStatusCode(int externalServiceHTTPStatusCode) {
		this.externalServiceHTTPStatusCode = externalServiceHTTPStatusCode;
	}

	public String getExternalResultStatusCode() {
		return externalResultStatusCode;
	}

	public void setExternalResultStatusCode(String externalResultStatusCode) {
		this.externalResultStatusCode = externalResultStatusCode;
	}
	
	
}
