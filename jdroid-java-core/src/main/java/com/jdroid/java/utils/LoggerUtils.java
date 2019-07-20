package com.jdroid.java.utils;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.logger.MuteLogger;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggerUtils {
	
	private static boolean enabled = false;
	private static ExceptionLogger exceptionLogger;
	
	private static final Logger MUTE_LOGGER = new MuteLogger();
	private static List<String> DISABLED_LOGGERS = Lists.INSTANCE.newArrayList();
	private static ILoggerFactory DEFAULT_LOGGER_FACTORY;
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getSimpleName());
	}
	
	public static Logger getLogger(String name) {
		if (enabled) {
			String loggerName = getLoggerName(name);
			if (!DISABLED_LOGGERS.contains(loggerName)) {
				if (DEFAULT_LOGGER_FACTORY != null) {
					return DEFAULT_LOGGER_FACTORY.getLogger(loggerName);
				} else {
					return LoggerFactory.getLogger(loggerName);
				}
			} else {
				return MUTE_LOGGER;
			}
		} else {
			return MUTE_LOGGER;
		}
	}
	
	private static String getLoggerName(String name) {
		// Logcat support 23 characters as maximum
		return name.substring(0, Math.min(name.length(), 23));
	}
	
	public static void setEnabled(boolean enabled) {
		LoggerUtils.enabled = enabled;
	}
	
	public static boolean isEnabled() {
		return enabled;
	}

	public static void setDefaultLoggerFactory(ILoggerFactory defaultLoggerFactory) {
		DEFAULT_LOGGER_FACTORY = defaultLoggerFactory;
	}

	public static void addDisabledLogger(String name) {
		DISABLED_LOGGERS.add(getLoggerName(name));
	}
	
	public static void addDisabledLogger(Class<?> clazz) {
		addDisabledLogger(clazz.getSimpleName());
	}
	
	/**
	 * Log the {@link Exception} on the {@link ExceptionLogger}. If it is null, the defaultLogger is used
	 *
	 * @param defaultLogger The {@link Logger} used if the {@link ExceptionLogger} is null
	 * @param e The {@link Exception} to log
	 */
	public static void logHandledException(Logger defaultLogger, Exception e) {
		if (exceptionLogger != null) {
			exceptionLogger.logHandledException(e);
		} else {
			defaultLogger.error(e.getMessage(), e);
		}
	}
	
	public static interface ExceptionLogger {
		
		public void logHandledException(Throwable throwable);
	}
	
	public static void setExceptionLogger(ExceptionLogger exceptionLogger) {
		LoggerUtils.exceptionLogger = exceptionLogger;
	}
}
