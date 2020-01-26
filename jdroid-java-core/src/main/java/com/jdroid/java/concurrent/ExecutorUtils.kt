package com.jdroid.java.concurrent

import com.jdroid.java.utils.LoggerUtils
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

object ExecutorUtils {

    private val LOGGER = LoggerUtils.getLogger(ExecutorUtils::class.java)

    // Default amount of thread inside the pool
    private const val DEFAULT_THREAD_POOL_SIZE = 5

    private val fixedExecutor = Executors.newCachedThreadPool(NormalPriorityThreadFactory())

    private val fixedLowPriorityExecutor = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE, LowPriorityThreadFactory())

    private val scheduledExecutor = Executors.newScheduledThreadPool(1, NormalPriorityThreadFactory("schedule"))

    /**
     * @param runnable The [Runnable] task
     */
    fun execute(runnable: Runnable) {
        fixedExecutor.execute(runnable)
    }

    fun executeWithLowPriority(runnable: Runnable) {
        fixedLowPriorityExecutor.execute(runnable)
    }

    fun schedule(runnable: Runnable, delay: Long?, timeUnit: TimeUnit) {
        scheduledExecutor.schedule(runnable, delay!!, timeUnit)
    }

    fun schedule(runnable: Runnable, delay: Long?, period: Long?, timeUnit: TimeUnit): ScheduledFuture<*> {
        return scheduledExecutor.scheduleAtFixedRate(runnable, delay!!, period!!, timeUnit)
    }

    fun sleep(delay: Int, timeUnit: TimeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(delay.toLong()))
        } catch (e: InterruptedException) {
            LoggerUtils.logHandledException(LOGGER, e)
        }
    }
}
