package com.jdroid.java.exception;


/**
 * Exception related with connectivity errors.
 */
public class ConnectionException extends ErrorCodeException {
	
	private static final long serialVersionUID = 7201698090980820539L;
	
	private boolean readTimeout = false;
	
	public ConnectionException(Throwable throwable) {
		super(CommonErrorCode.CONNECTION_ERROR, throwable);
	}
	
	public ConnectionException(String message) {
		super(CommonErrorCode.CONNECTION_ERROR, message);
	}
	
	public ConnectionException(Throwable throwable, boolean readTimeout) {
		this(throwable);
		this.readTimeout = readTimeout;
	}
	
	public boolean isReadTimeout() {
		return readTimeout;
	}
}
