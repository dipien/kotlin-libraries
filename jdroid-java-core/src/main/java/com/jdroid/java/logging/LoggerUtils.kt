package com.jdroid.java.logging

import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception
import kotlin.collections.MutableList

object LoggerUtils {

    var isEnabled: Boolean = false
    var exceptionLogger: ExceptionLogger? = null
    var defaultLoggerFactory: ILoggerFactory? = null

    private val muteLogger: Logger = MuteLogger()
    private val disabledLoggers: MutableList<String> = mutableListOf()

    @JvmStatic
    fun getLogger(clazz: Class<*>): Logger {
        return getLogger(clazz.getSimpleName())
    }

    fun getLogger(name: String): Logger {
        if (isEnabled) {
            val loggerName: String = getLoggerName(name)
            if (!disabledLoggers.contains(loggerName)) {
                if (defaultLoggerFactory != null) {
                    return defaultLoggerFactory!!.getLogger(loggerName)
                } else {
                    return LoggerFactory.getLogger(loggerName)
                }
            } else {
                return muteLogger
            }
        } else {
            return muteLogger
        }
    }

    private fun getLoggerName(name: String): String {
        // Logcat support 23 characters as maximum
        return name.substring(0, Math.min(name.length, 23))
    }

    fun addDisabledLogger(name: String) {
        disabledLoggers.add(getLoggerName(name))
    }

    fun addDisabledLogger(clazz: Class<*>) {
        addDisabledLogger(clazz.getSimpleName())
    }

    /**
     * Log the [Exception] on the [ExceptionLogger]. If it is null, the defaultLogger is used
     *
     * @param defaultLogger The [Logger] used if the [ExceptionLogger] is null
     * @param e The [Exception] to log
     */
    fun logHandledException(defaultLogger: Logger, e: Exception) {
        if (exceptionLogger != null) {
            exceptionLogger!!.logHandledException(e)
        } else {
            defaultLogger.error(e.message, e)
        }
    }
}
