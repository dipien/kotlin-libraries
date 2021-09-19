package com.dipien.core.analytics

import java.util.concurrent.Executor

interface AnalyticsTracker {

    fun isEnabled(): Boolean

    fun getExecutor(): Executor
}
