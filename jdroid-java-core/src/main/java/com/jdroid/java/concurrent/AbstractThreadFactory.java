package com.jdroid.java.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractThreadFactory implements ThreadFactory {
	
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;
	
	public AbstractThreadFactory(String namePrefix) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.namePrefix = namePrefix + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
	}
	
	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
		if (thread.isDaemon()) {
			thread.setDaemon(false);
		}
		thread.setPriority(getThreadsPriority());
		return thread;
	}
	
	protected abstract int getThreadsPriority();
	
}