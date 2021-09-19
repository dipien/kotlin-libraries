package com.dipien.core.concurrent

class NormalPriorityThreadFactory(namePrefix: String = "normal-prio") : AbstractThreadFactory(namePrefix) {

    override fun getThreadsPriority(): Int {
        return Thread.NORM_PRIORITY
    }
}
