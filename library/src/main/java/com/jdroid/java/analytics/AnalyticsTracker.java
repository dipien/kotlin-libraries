package com.jdroid.java.analytics;

import java.util.concurrent.Executor;

public interface AnalyticsTracker {

	public Boolean isEnabled();

	public Executor getExecutor();

}
