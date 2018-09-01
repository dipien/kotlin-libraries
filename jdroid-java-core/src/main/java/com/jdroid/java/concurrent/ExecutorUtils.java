package com.jdroid.java.concurrent;

import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class ExecutorUtils {
	
	private static final Logger LOGGER = LoggerUtils.getLogger(ExecutorUtils.class);
	
	// Default amount of thread inside the pool
	private static final int DEFAULT_THREAD_POOL_SIZE = 5;
	
	private static final Executor fixedExecutor = Executors.newCachedThreadPool(new NormalPriorityThreadFactory());
	
	private static final Executor fixedLowPriorityExecutor = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE,
		new LowPriorityThreadFactory());
	
	private static final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1,
		new NormalPriorityThreadFactory("schedule"));
	
	/**
	 * @param runnable The {@link Runnable} task
	 */
	public static void execute(Runnable runnable) {
		fixedExecutor.execute(runnable);
	}
	
	public static void executeWithLowPriority(Runnable runnable) {
		fixedLowPriorityExecutor.execute(runnable);
	}
	
	public static void schedule(Runnable runnable, Long delay, TimeUnit timeUnit) {
		scheduledExecutor.schedule(runnable, delay, timeUnit);
	}
	
	public static ScheduledFuture<?> schedule(Runnable runnable, Long delay, Long period, TimeUnit timeUnit) {
		return scheduledExecutor.scheduleAtFixedRate(runnable, delay, period, timeUnit);
	}
	
	public static void sleep(int delay, TimeUnit timeUnit) {
		try {
			Thread.sleep(timeUnit.toMillis(delay));
		} catch (InterruptedException e) {
			LoggerUtils.logHandledException(LOGGER, e);
		}
	}
}