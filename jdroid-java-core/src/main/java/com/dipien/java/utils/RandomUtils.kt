package com.dipien.java.utils

import java.util.Random
import kotlin.math.abs

object RandomUtils {

    private val RANDOM = Random()

    fun getLong(): Long {
        return abs(RANDOM.nextLong())
    }

    fun getInt(): Int {
        return abs(RANDOM.nextInt())
    }

    fun get16BitsInt(): Int {
        return abs(RANDOM.nextInt(16))
    }

    fun getInt(bound: Int): Int {
        return abs(RANDOM.nextInt(bound))
    }
}
