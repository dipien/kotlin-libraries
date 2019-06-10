package com.jdroid.java.logger;

import org.slf4j.helpers.MarkerIgnoringBase;

/**
 * A logger wrapper that filter all log requests.
 */
public class MuteLogger extends MarkerIgnoringBase {

	private static final long serialVersionUID = -6526350853255198576L;

	public MuteLogger() {

	}

	@Override
	public boolean isTraceEnabled() {
		return false;
	}

	@Override
	public void trace(final String msg) {
		// Ignore
	}

	@Override
	public void trace(final String format, final Object param1) {
		// Ignore
	}

	@Override
	public void trace(final String format, final Object param1, final Object param2) {
		// Ignore
	}

	@Override
	public void trace(final String format, final Object[] argArray) {
		// Ignore
	}

	@Override
	public void trace(final String msg, final Throwable t) {
		// Ignore
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public void debug(final String msg) {
		// Ignore
	}

	@Override
	public void debug(final String format, final Object arg1) {
		// Ignore
	}

	@Override
	public void debug(final String format, final Object param1, final Object param2) {
		// Ignore
	}

	@Override
	public void debug(final String format, final Object[] argArray) {
		// Ignore
	}

	@Override
	public void debug(final String msg, final Throwable t) {
		// Ignore
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	/**
	 * @see org.slf4j.Logger#info(java.lang.String)
	 */
	@Override
	public void info(final String msg) {
		// Ignore
	}

	@Override
	public void info(final String format, final Object arg) {
		// Ignore
	}

	@Override
	public void info(final String format, final Object arg1, final Object arg2) {
		// Ignore
	}

	@Override
	public void info(final String format, final Object[] argArray) {
		// Ignore
	}

	@Override
	public void info(final String msg, final Throwable t) {
		// Ignore
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public void warn(final String msg) {
		// Ignore
	}

	@Override
	public void warn(final String format, final Object arg) {
		// Ignore
	}

	@Override
	public void warn(final String format, final Object arg1, final Object arg2) {
		// Ignore
	}

	@Override
	public void warn(final String format, final Object[] argArray) {
		// Ignore
	}

	@Override
	public void warn(final String msg, final Throwable t) {
		// Ignore
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	@Override
	public void error(final String msg) {
		// Ignore
	}

	@Override
	public void error(final String format, final Object arg) {
		// Ignore
	}

	@Override
	public void error(final String format, final Object arg1, final Object arg2) {
		// Ignore
	}

	@Override
	public void error(final String format, final Object[] argArray) {
		// Ignore
	}

	@Override
	public void error(final String msg, final Throwable t) {
		// Ignore
	}

}
