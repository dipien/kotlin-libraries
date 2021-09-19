package com.dipien.java.exception

import java.io.PrintStream
import java.io.PrintWriter

abstract class AbstractException : RuntimeException {

    companion object {
        const val CRITICAL_PRIORITY = 10
        const val NORMAL_PRIORITY = 50
        const val LOW_PRIORITY = 90
    }

    private val parameters = hashMapOf<String, Any>()
    var title: String? = null
    var description: String? = null
    private var trackable: Boolean = true
    var isIgnoreStackTrace: Boolean = false
    var priorityLevel = NORMAL_PRIORITY

    constructor() : super()

    constructor(message: String, cause: Throwable?) : super(message, cause)

    constructor(message: String) : super(message)

    constructor(cause: Throwable?) : super(cause)

    fun getParameters(): Map<String, Any> {
        return parameters
    }

    fun hasParameter(key: String): Boolean {
        return parameters.containsKey(key)
    }

    @Suppress("UNCHECKED_CAST")
    fun <E> getParameter(key: String): E {
        return parameters[key] as E
    }

    fun addParameter(key: String, value: Any) {
        parameters[key] = value
    }

    fun isTrackable(): Boolean {
        return trackable
    }

    open fun setTrackable(trackable: Boolean): AbstractException {
        this.trackable = trackable
        return this
    }

    override fun getStackTrace(): Array<StackTraceElement>? {
        return if (isIgnoreStackTrace) null else super.getStackTrace()
    }

    override fun printStackTrace(err: PrintStream) {
        if (isIgnoreStackTrace) {
            return
        }
        super.printStackTrace(err)
    }

    override fun printStackTrace(err: PrintWriter) {
        if (isIgnoreStackTrace) {
            return
        }
        super.printStackTrace(err)
    }

    open fun getThrowableToLog(): Throwable {
        return this
    }
}
