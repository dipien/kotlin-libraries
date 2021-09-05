package com.jdroid.java.analytics

import com.jdroid.java.logging.LoggerUtils
import java.util.concurrent.Executor

open class AnalyticsSender<T : AnalyticsTracker>(trackers: List<T>) : AnalyticsTracker {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(AnalyticsSender::class.java)
    }

    private val trackers = mutableListOf<T>()

    @SafeVarargs
    constructor(vararg trackers: T) : this(listOf<T>(*trackers)) {
    }

    init {
        for (tracker in trackers) {
            if (tracker.isEnabled()) {
                this.trackers.add(tracker)
            }
        }
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getExecutor(): Executor {
        throw IllegalAccessException()
    }

    protected fun getTrackers(): List<T> {
        return trackers
    }

    fun addTracker(tracker: T) {
        trackers.add(tracker)
    }

    @JvmOverloads
    protected fun execute(trackingCommand: TrackingCommand, logHandledExceptionEnabled: Boolean = true) {
        for (tracker in getTrackers()) {
            try {
                if (tracker.isEnabled()) {
                    tracker.getExecutor().execute(TrackerRunnable(tracker, trackingCommand, logHandledExceptionEnabled))
                }
            } catch (e: Exception) {
                if (logHandledExceptionEnabled) {
                    LoggerUtils.logHandledException(LOGGER, e)
                } else {
                    LOGGER.error("Error when tracking.", e)
                }
            }
        }
    }

    abstract inner class TrackingCommand {
        abstract fun track(tracker: T)
    }

    private inner class TrackerRunnable(private val tracker: T, private val trackingCommand: TrackingCommand, private val logHandledExceptionEnabled: Boolean) : Runnable {

        override fun run() {
            try {
                trackingCommand.track(tracker)
            } catch (e: Exception) {
                if (logHandledExceptionEnabled) {
                    LoggerUtils.logHandledException(LOGGER, e)
                } else {
                    LOGGER.error("Error when tracking", e)
                }
            }
        }
    }
}
