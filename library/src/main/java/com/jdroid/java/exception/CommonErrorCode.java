package com.jdroid.java.exception;

public enum CommonErrorCode implements ErrorCode {
	
	CONNECTION_ERROR(null),
	UNEXPECTED_ERROR(null),
	HTTP_RESPONSE_ERROR(null);
	
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
	
	/**
	 * @see com.jdroid.java.exception.ErrorCode#getStatusCode()
	 */
	@Override
	public String getStatusCode() {
		return statusCode.toString();
	}
	
	/**
	 * @see com.jdroid.java.exception.ErrorCode#getTitleResId()
	 */
	@Override
	public Integer getTitleResId() {
		return null;
	}
	
	/**
	 * @see com.jdroid.java.exception.ErrorCode#getDescriptionResId()
	 */
	@Override
	public Integer getDescriptionResId() {
		return resourceId;
	}
	
	/**
	 * @see com.jdroid.java.exception.ErrorCode#newErrorCodeException(java.lang.Object[])
	 */
	@Override
	public ErrorCodeException newErrorCodeException(Object... errorCodeParameters) {
		return new ErrorCodeException(this, errorCodeParameters);
	}
	
	/**
	 * @see com.jdroid.java.exception.ErrorCode#newErrorCodeException()
	 */
	@Override
	public ErrorCodeException newErrorCodeException() {
		return new ErrorCodeException(this);
	}
	
	/**
	 * @see com.jdroid.java.exception.ErrorCode#newErrorCodeException(java.lang.Throwable)
	 */
	@Override
	public ErrorCodeException newErrorCodeException(Throwable throwable) {
		return new ErrorCodeException(this, throwable);
	}
	
	/**
	 * @see com.jdroid.java.exception.ErrorCode#newErrorCodeException(java.lang.String)
	 */
	@Override
	public ErrorCodeException newErrorCodeException(String message) {
		return new ErrorCodeException(this, message);
	}
}
