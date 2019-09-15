package com.jdroid.java.repository

import com.jdroid.java.collections.Lists
import com.jdroid.java.collections.Maps
import com.jdroid.java.domain.Identifiable
import com.jdroid.java.exception.UnexpectedException
import com.jdroid.java.utils.LoggerUtils
import com.jdroid.java.utils.ReflectionUtils

open class InMemoryRepository<T : Identifiable> : Repository<T> {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(InMemoryRepository::class.java)
    }

    private var nextId: Long = 1
    private val items = Maps.newLinkedHashMap<String, T>()

    override fun add(item: T) {
        if (item.getId() == null) {
            ReflectionUtils.setId(item, nextId++)
        }
        items[item.getId()!!] = item
        LOGGER.debug("Added object in memory: $item")
    }

    override fun addAll(items: Collection<T>) {
        for (item in items) {
            add(item)
        }
        LOGGER.debug("Stored objects in memory:\n$items")
    }

    override fun update(item: T) {
        if (item.getId() == null) {
            throw UnexpectedException("Item with null id can not be updated")
        }
        items[item.getId()!!] = item
        LOGGER.debug("Updated object in memory: $item")
    }

    override fun remove(item: T) {
        items.remove(item.getId())
        LOGGER.debug("Deleted object from memory: $item")
    }

    override fun removeAll(items: Collection<T>) {
        for (item in items) {
            remove(item)
        }
        LOGGER.debug("Deleted objects in memory: $items")
    }

    override fun replaceAll(items: Collection<T>) {
        removeAll()
        addAll(items)
    }

    override fun getAll(): List<T> {
        val results = Lists.newArrayList(items.values)
        LOGGER.debug("Retrieved all objects [" + results.size + "] from memory")
        return results
    }

    override fun get(id: String): T? {
        return items[id]
    }

    override fun removeAll() {
        items.clear()
        LOGGER.debug("Deleted from memory all objects from this repository")
    }

    override fun remove(id: String) {
        items.remove(id)
    }

    override fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    override fun getSize(): Long {
        return items.size.toLong()
    }

    override fun getByField(fieldName: String, vararg values: Any): List<T> {
        throw UnsupportedOperationException()
    }

    override fun getItemByField(fieldName: String, vararg values: Any): T? {
        throw UnsupportedOperationException()
    }

    override fun getByIds(ids: List<String>): List<T> {
        val itemsList = mutableListOf<T>()
        for (each in ids) {
            val item = items[each]
            if (item != null) {
                itemsList.add(item)
            }
        }
        return itemsList
    }

    override fun getUniqueInstance(): T? {
        return if (items.isEmpty()) null else items.values.iterator().next()
    }
}
