package com.jdroid.java.exception;

import com.jdroid.java.collections.Maps;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

public abstract class AbstractException extends RuntimeException {
	
	private static final long serialVersionUID = 6296155655850331666L;

	public static final int CRITICAL_PRIORITY = 10;
	public static final int NORMAL_PRIORITY = 50;
	public static final int LOW_PRIORITY = 90;

	private Map<String, Object> parameters = Maps.newHashMap();
	private String title;
	private String description;
	private Boolean trackable = true;
	private Boolean ignoreStackTrace = false;
	private int priorityLevel = NORMAL_PRIORITY;

	public AbstractException() {
		super();
	}
	
	public AbstractException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AbstractException(String message) {
		super(message);
	}
	
	public AbstractException(Throwable cause) {
		super(cause);
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public Boolean hasParameter(String key) {
		return parameters.containsKey(key);
	}
	
	@SuppressWarnings("unchecked")
	public <E> E getParameter(String key) {
		return (E)parameters.get(key);
	}
	
	public void addParameter(String key, Object value) {
		parameters.put(key, value);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean isTrackable() {
		return trackable;
	}
	
	public AbstractException setTrackable(Boolean trackable) {
		this.trackable = trackable;
		return this;
	}
	
	public Throwable getThrowableToLog() {
		return this;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return ignoreStackTrace ? null : super.getStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream err) {
		if (ignoreStackTrace) {
			return;
		}
		super.printStackTrace(err);
	}

	@Override
	public void printStackTrace(PrintWriter err) {
		if (ignoreStackTrace) {
			return;
		}
		super.printStackTrace(err);
	}

	public void setIgnoreStackTrace(Boolean ignoreStackTrace) {
		this.ignoreStackTrace = ignoreStackTrace;
	}

	public Boolean isIgnoreStackTrace() {
		return ignoreStackTrace;
	}
}
