package com.jdroid.java.repository

import com.jdroid.java.utils.ReflectionUtils

/**
 * Represent all those objects that could be identifiable by an ID
 */
interface Identifiable {

    companion object {
        const val ID_FIELD = "id"

        fun setIdByReflection(identifiable: Identifiable?, id: Any?) {
            ReflectionUtils.set(identifiable, ID_FIELD, id)
        }
    }

    /**
     * Gets the identification for this [Identifiable]
     *
     * @return the id of this [Identifiable]
     */
    fun getId(): String?
}
