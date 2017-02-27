package com.jdroid.java.analytics;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.Executor;

public class AnalyticsSender<T extends AnalyticsTracker> implements AnalyticsTracker {

	private static final Logger LOGGER = LoggerUtils.getLogger(AnalyticsSender.class);

	private List<T> trackers = Lists.newArrayList();

	@SafeVarargs
	public AnalyticsSender(T... trackers) {
		this(Lists.newArrayList(trackers));
	}

	public AnalyticsSender(List<T> trackers) {
		for (T tracker : trackers) {
			if (tracker.isEnabled()) {
				this.trackers.add(tracker);
			}
		}
	}

	@Override
	public Boolean isEnabled() {
		return null;
	}

	@Override
	public Executor getExecutor() {
		return null;
	}

	protected List<T> getTrackers() {
		return trackers;
	}

	public void addTracker(T tracker) {
		trackers.add(tracker);
	}

	protected void execute(TrackingCommand trackingCommand, Boolean logHandledExceptionEnabled) {
		for (final T tracker : getTrackers()) {
			try {
				if (tracker.isEnabled()) {
					tracker.getExecutor().execute(new TrackerRunnable(tracker, trackingCommand, logHandledExceptionEnabled));
				}
			} catch (Exception e) {
				if (logHandledExceptionEnabled) {
					LoggerUtils.logHandledException(LOGGER, e);
				} else {
					LOGGER.error("Error when tracking.", e);
				}
			}
		}
	}

	protected void execute(TrackingCommand trackingCommand) {
		execute(trackingCommand, true);
	}


	public abstract class TrackingCommand {
		protected abstract void track(T tracker);
	}

	private class TrackerRunnable implements Runnable {

		private T tracker;
		private TrackingCommand trackingCommand;
		private Boolean logHandledExceptionEnabled;

		public TrackerRunnable(T tracker, TrackingCommand trackingCommand, Boolean logHandledExceptionEnabled) {
			this.tracker = tracker;
			this.trackingCommand = trackingCommand;
			this.logHandledExceptionEnabled = logHandledExceptionEnabled;
		}

		@Override
		public void run() {
			try {
				trackingCommand.track(tracker);
			} catch (Exception e) {
				if (logHandledExceptionEnabled) {
					LoggerUtils.logHandledException(LOGGER, e);
				} else {
					LOGGER.error("Error when tracking", e);
				}
			}
		}
	}

}
