package com.dipien.java.search

import java.util.Collections
import java.util.Comparator

/**
 * Represents a list result in a paginated API call.
 *
 * @param <T> The list item.
</T> */
open class PagedResult<T> @JvmOverloads constructor(private val results: MutableList<T>, private val lastPage: Boolean = true) {

    /**
     * @return the lastPage
     */
    val isLastPage: Boolean
        get() = lastPage

    /**
     * @param lastPage Whether the paginates list contains the last page or not.
     */
    @JvmOverloads
    constructor(lastPage: Boolean = true) : this(mutableListOf(), lastPage) {
    }

    /**
     * Adds a result item to the list.
     *
     * @param result The result to add.
     */
    fun addResult(result: T) {
        results.add(result)
    }

    /**
     * Adds result items to the list.
     *
     * @param results The results to add.
     */
    fun addResults(results: Collection<T>) {
        this.results.addAll(results)
    }

    /**
     * @return the results
     */
    fun getResults(): List<T> {
        return results
    }

    fun sortResults(comparator: Comparator<T>) {
        Collections.sort(results, comparator)
    }
}
