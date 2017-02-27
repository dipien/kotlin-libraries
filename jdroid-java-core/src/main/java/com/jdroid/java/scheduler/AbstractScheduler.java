package com.jdroid.java.scheduler;

import com.jdroid.java.date.DateUtils;

import java.util.Date;

public class AbstractScheduler {
	
	private Boolean inProgress = false;
	private Date executionStartDate;
	private Date executionEndDate;
	
	public synchronized Boolean acquireLock() {
		if (!inProgress) {
			inProgress = true;
			executionStartDate = DateUtils.now();
			return true;
		}
		return false;
	}
	
	public synchronized void releaseLock() {
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
