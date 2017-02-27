package com.jdroid.java.exception;


public class UnexpectedException extends ErrorCodeException {
	
	private static final long serialVersionUID = 7653653326088130933L;
	
	private Boolean useCause = false;
	
	public UnexpectedException(String message, Throwable cause) {
		super(CommonErrorCode.UNEXPECTED_ERROR, message, cause);
		setTrackable(true);
	}
	
	public UnexpectedException(String message) {
		super(CommonErrorCode.UNEXPECTED_ERROR, message);
		setTrackable(true);
	}
	
	public UnexpectedException(Throwable cause) {
		super(CommonErrorCode.UNEXPECTED_ERROR, cause);
		setTrackable(true);
		useCause = true;
	}
	
	@Override
	public Throwable getThrowableToLog() {
		return (getCause() != null) && useCause ? getCause() : super.getThrowableToLog();
	}
}