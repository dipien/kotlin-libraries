package com.dipien.java.repository

/**
 * Interface that all repositories must adhere to. It provides basic repository functionality.
 *
 * @param <T> The type of Identifiable
 */
interface Repository<T : Identifiable> {

    /**
     * Retrieves an [Identifiable] from the repository according to an id or null in case no [Identifiable]
     * with the given id is not found
     *
     * @param id the id for the [Identifiable] to retrieve
     * @return the [Identifiable] retrieved.
     */
    fun get(id: String): T?

    /**
     * Obtains a list containing all the [Identifiable]s in the repository
     *
     * @return the list of [Identifiable]s
     */
    fun getAll(): List<T>

    /**
     * @return The unique instance
     */
    fun getUniqueInstance(): T?

    /**
     * @param ids
     * @return All the items with the ids
     */
    fun getByIds(ids: List<String>): List<T>

    /**
     * @param fieldName
     * @param values
     * @return items the items with the fieldName that match with values.
     */
    fun getByField(fieldName: String, vararg values: Any): List<T>

    /**
     * @param fieldName
     * @param values
     * @return the [Identifiable] retrieved.
     */
    fun getItemByField(fieldName: String, vararg values: Any): T?

    /**
     * @return If the repository has data or not
     */
    fun isEmpty(): Boolean

    fun getSize(): Long

    /**
     * Adds an [Identifiable] to the repository.
     *
     * @param item The [Identifiable] to add.
     */
    fun add(item: T)

    /**
     * Adds a collection of [Identifiable]s to the repository.
     *
     * @param items The [Identifiable]s to add.
     */
    fun addAll(items: Collection<T>)

    /**
     * Update an [Identifiable] on the repository.
     *
     * @param item The [Identifiable] to update.
     */
    fun update(item: T)

    /**
     * Removes an [Identifiable] from the repository.
     *
     * @param item The [Identifiable] to remove
     */
    fun remove(item: T)

    /**
     * Removes all the [Identifiable]s that the repository has.
     */
    fun removeAll()

    fun removeAll(items: Collection<T>)

    /**
     * Removes the [Identifiable] with the id
     *
     * @param id The [Identifiable] id to be removed
     */
    fun remove(id: String)

    /**
     * Replaces all the [Identifiable]s in the repository by new ones.
     *
     * @param items The new [Identifiable]s to replace the old ones.
     */
    fun replaceAll(items: Collection<T>)
}
