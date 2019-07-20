package com.jdroid.java.collections

import java.util.ArrayList
import java.util.Collections
import java.util.LinkedList

object Lists {

    // ArrayList

    /**
     * Creates a *mutable*, empty `ArrayList` instance.
     *
     * @return a new, empty `ArrayList`
     */
    fun <E> newArrayList(): ArrayList<E> {
        return ArrayList()
    }

    /**
     * Creates a *mutable* `ArrayList` instance containing the given elements.
     *
     * @param elements the elements that the list should contain, in order
     * @return a new `ArrayList` containing those elements
     */
    @SafeVarargs
    fun <E> newArrayList(vararg elements: E): ArrayList<E> {
        val list = ArrayList<E>()
        Collections.addAll<E>(list, *elements)
        return list
    }

    fun <E> safeArrayList(list: List<E>?): List<E> {
        return list ?: Lists.newArrayList()
    }

    /**
     * Creates a *mutable* `ArrayList` instance containing the given elements.
     *
     * @param elements the elements that the list should contain, in order
     * @return a new `ArrayList` containing those elements
     */
    fun <E> newArrayList(elements: Iterable<E>): ArrayList<E> {
        return if (elements is Collection<*>)
            ArrayList(elements as Collection<E>)
        else
            newArrayList(elements.iterator())
    }

    /**
     * Creates a *mutable* `ArrayList` instance containing the given elements.
     *
     * @param elements the elements that the list should contain, in order
     * @return a new `ArrayList` containing those elements
     */
    fun <E> newArrayList(elements: Iterator<E>): ArrayList<E> {
        val list = newArrayList<E>()
        while (elements.hasNext()) {
            list.add(elements.next())
        }
        return list
    }

    // LinkedList

    /**
     * Creates an empty `LinkedList` instance.
     *
     * @return a new, empty `LinkedList`
     */
    fun <E> newLinkedList(): LinkedList<E> {
        return LinkedList()
    }

    /**
     * Creates a `LinkedList` instance containing the given elements.
     *
     * @param elements the elements that the list should contain, in order
     * @return a new `LinkedList` containing those elements
     */
    fun <E> newLinkedList(elements: Iterable<E>): LinkedList<E> {
        val list = newLinkedList<E>()
        for (element in elements) {
            list.add(element)
        }
        return list
    }

    /**
     * @param list
     * @param maxCount
     * @return same list if size doesn't exceeds maxCount, new list with trimmed element otherwise
     */
    fun <T> trim(list: List<T>, maxCount: Int): List<T> {
        return if (list.size > maxCount) {
            Lists.newArrayList(list.subList(0, maxCount))
        } else list
    }

    @Deprecated("Use kotlin")
    fun isNullOrEmpty(list: List<*>?): Boolean {
        return list == null || list.isEmpty()
    }

    fun intersect(a: Collection<*>, b: Collection<*>): Boolean {
        return !Collections.disjoint(a, b)
    }
}
