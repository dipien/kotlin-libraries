package com.jdroid.java.scheduler

import com.jdroid.java.date.DateUtils
import com.jdroid.java.utils.LoggerUtils

import java.util.Date

// TODO Move this class to jdroid-javaweb
abstract class AbstractScheduler {

    private var inProgress: Boolean = false
    var lastExecutionStartDate: Date? = null
        private set
    var lastExecutionEndDate: Date? = null
        private set

    protected fun execute() {
        if (isEnabled()) {
            if (acquireLock()) {
                try {
                    doExecute()
                } catch (e: Exception) {
                    onException(e)
                } finally {
                    releaseLock()
                }
            } else {
                LoggerUtils.getLogger(javaClass).info(javaClass.simpleName + " already in progress, skipping this schedule.")
            }
        } else {
            LoggerUtils.getLogger(javaClass).info(javaClass.simpleName + " disabled, skipping this schedule.")
        }
    }

    protected abstract fun doExecute()

    protected fun onException(e: Exception) {
        LoggerUtils.getLogger(javaClass).error("Unexpected error when executing " + javaClass.simpleName, e)
    }

    protected fun isEnabled(): Boolean {
        return true
    }

    @Synchronized
    private fun acquireLock(): Boolean {
        if (!inProgress) {
            inProgress = true
            lastExecutionStartDate = DateUtils.now()
            return true
        }
        return false
    }

    @Synchronized
    private fun releaseLock() {
        inProgress = false
        lastExecutionEndDate = Date()
    }
}
