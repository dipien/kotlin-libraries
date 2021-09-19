package com.dipien.logging

import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception
import kotlin.collections.MutableList
import kotlin.math.min

object LoggerUtils {

    var isEnabled: Boolean = false
    var exceptionLogger: ExceptionLogger? = null
    var defaultLoggerFactory: ILoggerFactory? = null

    private val muteLogger: Logger = MuteLogger()
    private val disabledLoggers: MutableList<String> = mutableListOf()

    @JvmStatic
    fun getLogger(clazz: Class<*>): Logger {
        return getLogger(clazz.simpleName)
    }

    fun getLogger(name: String): Logger {
        return if (isEnabled) {
            val loggerName: String = getLoggerName(name)
            if (!disabledLoggers.contains(loggerName)) {
                if (defaultLoggerFactory != null) {
                    defaultLoggerFactory!!.getLogger(loggerName)
                } else {
                    LoggerFactory.getLogger(loggerName)
                }
            } else {
                muteLogger
            }
        } else {
            muteLogger
        }
    }

    private fun getLoggerName(name: String): String {
        // Logcat support 23 characters as maximum
        return name.substring(0, min(name.length, 23))
    }

    fun addDisabledLogger(name: String) {
        disabledLoggers.add(getLoggerName(name))
    }

    fun addDisabledLogger(clazz: Class<*>) {
        addDisabledLogger(clazz.simpleName)
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
