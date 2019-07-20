package com.jdroid.java.collections

import java.util.HashMap
import java.util.LinkedHashMap
import java.util.concurrent.ConcurrentHashMap

object Maps {

    /**
     * Creates a *mutable*, empty `HashMap` instance.
     *
     * @return a new, empty `HashMap`
     */
    fun <K, V> newHashMap(): HashMap<K, V> {
        return HashMap()
    }

    /**
     * Creates a *mutable*, empty, insertion-ordered `LinkedHashMap` instance.
     *
     * @return a new, empty `LinkedHashMap`
     */
    fun <K, V> newLinkedHashMap(): LinkedHashMap<K, V> {
        return LinkedHashMap()
    }

    fun <K, V> newConcurrentHashMap(): ConcurrentHashMap<K, V> {
        return ConcurrentHashMap()
    }
}
