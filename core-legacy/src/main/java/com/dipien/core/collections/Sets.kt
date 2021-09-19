package com.dipien.core.collections

import java.util.HashSet
import java.util.LinkedHashSet
import java.util.TreeSet

object Sets {

    // HashSet

    /**
     * Creates a *mutable*, empty `HashSet` instance.
     *
     * @return a new, empty `HashSet`
     */
    @Deprecated("")
    fun <E> newHashSet(): HashSet<E> {
        return HashSet()
    }

    /**
     * Creates a *mutable* `HashSet` instance containing the given elements in unspecified order.
     *
     * @param elements the elements that the set should contain
     * @return a new `HashSet` containing those elements (minus duplicates)
     */
    @Deprecated("")
    fun <E> newHashSet(elements: Iterable<E>): HashSet<E> {
        return if (elements is Collection<*>)
            HashSet(elements as Collection<E>)
        else
            newHashSet(elements.iterator())
    }

    /**
     * Creates a *mutable* `HashSet` instance containing the given elements in unspecified order.
     *
     * @param elements the elements that the set should contain
     * @return a new `HashSet` containing those elements (minus duplicates)
     */
    fun <E> newHashSet(elements: Iterator<E>): HashSet<E> {
        val set = newHashSet<E>()
        while (elements.hasNext()) {
            set.add(elements.next())
        }
        return set
    }

    // LinkedHashSet

    /**
     * Creates a *mutable*, empty `LinkedHashSet` instance.
     *
     * @return a new, empty `LinkedHashSet`
     */
    fun <E> newLinkedHashSet(): LinkedHashSet<E> {
        return LinkedHashSet()
    }

    /**
     * Creates a *mutable* `LinkedHashSet` instance containing the given elements in order.
     *
     * @param elements the elements that the set should contain, in order
     * @return a new `LinkedHashSet` containing those elements (minus duplicates)
     */
    fun <E> newLinkedHashSet(elements: Iterable<E>): LinkedHashSet<E> {
        if (elements is Collection<*>) {
            return LinkedHashSet(elements as Collection<E>)
        }
        val set = newLinkedHashSet<E>()
        for (element in elements) {
            set.add(element)
        }
        return set
    }

    // TreeSet

    /**
     * Creates a *mutable*, empty `TreeSet` instance sorted by the natural sort ordering of its elements.
     *
     * @return a new, empty `TreeSet`
     */
    fun <E : Comparable<*>> newTreeSet(): TreeSet<E> {
        return TreeSet()
    }

    /**
     * Creates a *mutable* `TreeSet` instance containing the given elements sorted by their natural ordering.
     *
     * @param elements the elements that the set should contain
     * @return a new `TreeSet` containing those elements (minus duplicates)
     */
    fun <E : Comparable<*>> newTreeSet(elements: Iterable<E>): TreeSet<E> {
        val set = newTreeSet<E>()
        for (element in elements) {
            set.add(element)
        }
        return set
    }
}
