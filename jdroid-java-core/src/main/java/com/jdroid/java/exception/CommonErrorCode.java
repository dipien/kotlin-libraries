package com.jdroid.java.exception;

public enum CommonErrorCode implements ErrorCode {
	
	UNEXPECTED_ERROR(null);
	
	private Integer resourceId;
	private Integer statusCode;
	
	private CommonErrorCode(Integer resourceId, Integer statusCode) {
		this.resourceId = resourceId;
		this.statusCode = statusCode;
	}
	
	private CommonErrorCode(Integer resourceId) {
		this.resourceId = resourceId;
	}
	
	public static ErrorCode findByStatusCode(String statusCode) {
		ErrorCode errorCode = null;
		for (CommonErrorCode each : values()) {
			if ((each.statusCode != null) && each.statusCode.toString().equals(statusCode)) {
				errorCode = each;
				break;
			}
		}
		return errorCode;
	}
	
	@Override
	public String getStatusCode() {
		return statusCode.toString();
	}
	
	@Override
	public Integer getTitleResId() {
		return null;
	}
	
	@Override
	public Integer getDescriptionResId() {
		return resourceId;
	}
	
	@Override
	public ErrorCodeException newErrorCodeException(Object... errorCodeParameters) {
		return new ErrorCodeException(this, errorCodeParameters);
	}
	
	@Override
	public ErrorCodeException newErrorCodeException() {
		return new ErrorCodeException(this);
	}
	
	@Override
	public ErrorCodeException newErrorCodeException(Throwable throwable) {
		return new ErrorCodeException(this, throwable);
	}
	
	@Override
	public ErrorCodeException newErrorCodeException(String message) {
		return new ErrorCodeException(this, message);
	}
}
