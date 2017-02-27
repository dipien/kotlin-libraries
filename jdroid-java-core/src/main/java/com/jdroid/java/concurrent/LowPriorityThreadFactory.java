package com.jdroid.java.concurrent;

public class LowPriorityThreadFactory extends AbstractThreadFactory {
	
	public LowPriorityThreadFactory(String namePrefix) {
		super(namePrefix);
	}

	public LowPriorityThreadFactory() {
		this("low-prio");
	}

	@Override
	protected int getThreadsPriority() {
		return Thread.MIN_PRIORITY;
	}
}