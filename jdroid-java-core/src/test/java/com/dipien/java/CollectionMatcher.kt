package com.dipien.java

import org.mockito.ArgumentMatcher

/**
 * Matcher that verify the argument is contained by the collection
 *
 * @param <E> The collection type
</E> */
class CollectionMatcher<E>(private val collection: Collection<E>) : ArgumentMatcher<E>() {

    @Suppress("UNCHECKED_CAST")
    override fun matches(argument: Any): Boolean {
        return this.collection.contains(argument as E)
    }
}
