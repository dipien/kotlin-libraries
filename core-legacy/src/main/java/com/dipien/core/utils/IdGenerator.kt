package com.dipien.core.utils

object IdGenerator {

    private var ID: Int = 10000

    @Synchronized
    fun getLongId(): Long {
        ID++
        return ID.toLong()
    }

    @Synchronized
    fun getIntId(): Int {
        return ID++
    }
}
