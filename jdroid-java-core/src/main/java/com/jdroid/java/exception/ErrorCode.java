package com.jdroid.java.exception;

/**
 * Common interface for all the possible errors of the application
 */
public interface ErrorCode {
	
	/**
	 * @param errorCodeParameters The parameters for this {@link ErrorCode}'s message.
	 * @return A new {@link ErrorCodeException} with this {@link ErrorCode}
	 */
	public ErrorCodeException newErrorCodeException(Object... errorCodeParameters);
	
	public ErrorCodeException newErrorCodeException(Throwable throwable);
	
	/**
	 * @return A new {@link ErrorCodeException}
	 */
	public ErrorCodeException newErrorCodeException();
	
	/**
	 * @param message The message
	 * @return A new {@link ErrorCodeException}
	 */
	public ErrorCodeException newErrorCodeException(String message);
	
	/**
	 * @return The title resource id
	 */
	public Integer getTitleResId();
	
	/**
	 * @return The description resource id
	 */
	public Integer getDescriptionResId();
	
	/**
	 * @return The status code
	 */
	public String getStatusCode();
	
}
