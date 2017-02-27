package com.jdroid.java.exception;


/**
 * Exception thrown when there are unknown HTTP errors while communicating with the server.
 */
public class HttpResponseException extends ErrorCodeException {
	
	private static final long serialVersionUID = -1165226174367435109L;
	
	public HttpResponseException(String message) {
		super(CommonErrorCode.HTTP_RESPONSE_ERROR, message);
		setTrackable(true);
	}
	
}
