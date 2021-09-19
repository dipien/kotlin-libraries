package com.dipien.java.collections

object Iterables {

    /**
     * Returns the number of elements in `iterable`.
     *
     * @param iterable The [Iterable]
     * @return The [Iterable] size
     */
    fun size(iterable: Iterable<*>): Int {
        return (iterable as? Collection<*>)?.size ?: Iterators.size(iterable.iterator())
    }

    /**
     * Converts an iterable into a collection. If the iterable is already a collection, it is returned. Otherwise, an
     * [java.util.ArrayList] is created with the contents of the iterable in the same iteration order.
     */
    private fun <E> toCollection(iterable: Iterable<E>): Collection<E> {
        return if (iterable is Collection<*>) iterable as Collection<E> else Lists.newArrayList(iterable.iterator())
    }
}
