package com.dipien.java.collections

object Iterators {

    /**
     * Returns the number of elements remaining in `iterator`. The iterator will be left exhausted: its
     * `hasNext()` method will return `false`.
     *
     * @param iterator The [Iterator]
     * @return The [Iterator] size
     */
    fun size(iterator: Iterator<*>): Int {
        var count = 0
        while (iterator.hasNext()) {
            iterator.next()
            count++
        }
        return count
    }
}
