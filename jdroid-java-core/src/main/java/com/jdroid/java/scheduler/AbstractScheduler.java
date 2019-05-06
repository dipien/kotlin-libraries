package com.jdroid.java.scheduler;

import com.jdroid.java.date.DateUtils;
import com.jdroid.java.utils.LoggerUtils;

import java.util.Date;

public abstract class AbstractScheduler {
	
	private Boolean inProgress = false;
	private Date executionStartDate;
	private Date executionEndDate;
	
	protected void execute() {
		if (isEnabled()) {
			if (acquireLock()) {
				try {
					doExecute();
				} catch (Exception e) {
					onException(e);
				} finally {
					releaseLock();
				}
			} else {
				LoggerUtils.getLogger(getClass()).info(getClass().getSimpleName() + " already in progress, skipping this schedule.");
			}
		} else {
			LoggerUtils.getLogger(getClass()).info(getClass().getSimpleName() + " disabled, skipping this schedule.");
		}
	}
	
	protected abstract void doExecute();
	
	protected void onException(Exception e) {
		LoggerUtils.getLogger(getClass()).error("Unexpected error when executing " + getClass().getSimpleName(), e);
	}
	
	protected Boolean isEnabled() {
		return true;
	}
	
	private synchronized Boolean acquireLock() {
		if (!inProgress) {
			inProgress = true;
			executionStartDate = DateUtils.now();
			return true;
		}
		return false;
	}
	
	private synchronized void releaseLock() {
		inProgress = false;
		executionEndDate = new Date();
	}
	
	public Date getLastExecutionStartDate() {
		return executionStartDate;
	}
	
	public Date getLastExecutionEndDate() {
		return executionEndDate;
	}
	
}
