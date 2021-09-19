package com.dipien.java.repository

/**
 * Represent all those objects that could be identifiable by an ID
 */
interface Identifiable {

    companion object {
        const val ID_FIELD = "id"
    }

    /**
     * Gets the identification for this [Identifiable]
     *
     * @return the id of this [Identifiable]
     */
    fun getId(): String?
}
