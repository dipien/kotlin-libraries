package com.jdroid.java.collections

/**
 * A set of useful functions used against a Collection
 */
object CollectionUtils {
    /**
     * Null-safe check if the specified collection is empty.
     *
     *
     * Null returns true.
     *
     * @param coll the collection to check, may be null
     * @return true if empty or null
     */
    @Deprecated("Use kotlin")
    fun isEmpty(coll: Collection<*>?): Boolean {
        return coll == null || coll.isEmpty()
    }

    fun isEmptyCollection(`object`: Any): Boolean {
        return (`object` as? Collection<*>)?.let { isEmpty(it) } ?: false
    }

    /**
     * Null-safe check if the specified collection is not empty.
     *
     *
     * Null returns false.
     *
     * @param coll the collection to check, may be null
     * @return true if non-null and non-empty
     */
    @Deprecated("Use kotlin")
    fun isNotEmpty(coll: Collection<*>): Boolean {
        return !CollectionUtils.isEmpty(coll)
    }
}
