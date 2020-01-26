package com.jdroid.java.concurrent

class LowPriorityThreadFactory(namePrefix: String = "low-prio") : AbstractThreadFactory(namePrefix) {

    override fun getThreadsPriority(): Int {
        return Thread.MIN_PRIORITY
    }
}
