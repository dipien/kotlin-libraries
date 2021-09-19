package com.dipien.java.concurrent

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

abstract class AbstractThreadFactory(namePrefix: String) : ThreadFactory {

    companion object {
        private val poolNumber = AtomicInteger(1)
    }

    private val group: ThreadGroup
    private val threadNumber = AtomicInteger(1)
    private val namePrefix: String

    init {
        val s = System.getSecurityManager()
        group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
        this.namePrefix = namePrefix + "-pool-" + poolNumber.getAndIncrement() + "-thread-"
    }

    override fun newThread(runnable: Runnable): Thread {
        val thread = Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0)
        if (thread.isDaemon) {
            thread.isDaemon = false
        }
        thread.priority = getThreadsPriority()
        return thread
    }

    protected abstract fun getThreadsPriority(): Int
}
