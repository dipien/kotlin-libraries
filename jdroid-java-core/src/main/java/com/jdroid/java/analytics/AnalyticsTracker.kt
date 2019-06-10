package com.jdroid.java.analytics

import java.util.concurrent.Executor

interface AnalyticsTracker {

    fun isEnabled(): Boolean

    fun getExecutor(): Executor
}
