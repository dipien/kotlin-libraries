package com.dipien.repository

import java.io.Serializable

/**
 * Domain Entity
 */
abstract class Entity(private var id: String? = null) : Serializable, Identifiable {

    companion object {
        private const val serialVersionUID = 907671509045298947L
    }

    /**
     * @param parentId the parentId to set
     */
    var parentId: String? = null

    /**
     * Gets the identification for this [Entity]
     *
     * @return the id of this [Entity]
     */
    override fun getId(): String? {
        return id
    }

    /**
     * @param id the id to set
     */
    fun setId(id: String) {
        this.id = id
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Entity{id='")
        builder.append(id)
        if (parentId != null) {
            builder.append("\', parentId='")
            builder.append(parentId)
        }
        builder.append("\'}")
        return builder.toString()
    }

    /**
     * Since equality has been redefined, so must be the hashCode function.
     *
     * @see java.lang.Object.hashCode
     */
    override fun hashCode(): Int {
        val prime = 31
        return if (id != null) prime * id!!.hashCode() else super.hashCode()
    }

    /**
     * Redefines equality depending on the id of the entities being compared.
     *
     * @see java.lang.Object.equals
     */
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (!javaClass.isAssignableFrom(other.javaClass)) {
            return false
        }
        val otherEntity = other as Entity?

        if (id != null) {
            return id == otherEntity!!.getId()
        }

        return if (otherEntity!!.getId() != null) {
            false
        } else super.equals(otherEntity)
    }
}
