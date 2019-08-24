package com.jdroid.java.repository

import com.jdroid.java.collections.Lists
import com.jdroid.java.collections.Maps
import com.jdroid.java.domain.Identifiable
import com.jdroid.java.exception.UnexpectedException
import com.jdroid.java.utils.LoggerUtils

open class CacheWrapperRepository<T : Identifiable>(protected val wrappedRepository: Repository<T>) : Repository<T> {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(CacheWrapperRepository::class.java)
    }

    val cache: MutableMap<String, T> by lazy { createCacheMap() }
    private val cachedIds: MutableList<String> = mutableListOf()
    protected open var isSynced: Boolean = false

    val cachedItems: List<T>
        get() = Lists.newArrayList(cache.values)

    protected open fun createCacheMap(): MutableMap<String, T> {
        return Maps.newConcurrentHashMap()
    }

    override operator fun get(id: String): T? {
        val item: T?

        if (isSynced && !cachedIds.contains(id)) {
            cachedIds.add(id)
        }

        if (cachedIds.contains(id)) {
            item = cache[id]
            if (item != null) {
                LOGGER.debug("Retrieved cached object: " + item.javaClass.simpleName + ". [ " + item + " ]")
            } else {
                LOGGER.debug("Retrieved cached object with id: $id. [ $item ]")
            }
        } else {
            item = wrappedRepository.get(id)
            if (item != null) {
                cache[id] = item
            }
            cachedIds.add(id)
        }
        return item
    }

    override fun add(item: T) {
        wrappedRepository.add(item)
        addToCache(item)
    }

    override fun addAll(items: Collection<T>) {
        wrappedRepository.addAll(items)
        for (each in items) {
            addToCache(each)
        }
    }

    override fun update(item: T) {
        wrappedRepository.update(item)
        addToCache(item)
    }

    override fun remove(item: T) {
        wrappedRepository.remove(item)
        removeFromCache(item)
    }

    override fun removeAll() {
        wrappedRepository.removeAll()
        cache.clear()
        cachedIds.clear()
        isSynced = true
    }

    override fun removeAll(items: Collection<T>) {
        wrappedRepository.removeAll(items)
        for (each in items) {
            addToCache(each)
            removeFromCache(each)
        }
    }

    override fun getByField(fieldName: String, vararg values: Any): List<T> {
        // TODO Add cache to getByField query
        LOGGER.info("The getByField query is not cached. Repository [" + wrappedRepository.javaClass.simpleName + "]. Field name [" + fieldName + "]")
        return wrappedRepository.getByField(fieldName, *values)
    }

    override fun getItemByField(fieldName: String, vararg values: Any): T? {
        // TODO Add cache to getItemByField query
        LOGGER.info("The getItemByField query is not cached. Repository [" + wrappedRepository.javaClass.simpleName + "]. Field name [" + fieldName + "]")
        return wrappedRepository.getItemByField(fieldName, *values)
    }

    override fun getAll(): List<T> {
        return if (isSynced) {
            val items = Lists.newArrayList(cache.values)
            LOGGER.info("Retrieved all cached objects [" + items.size + "]")
            items
        } else {
            val items = wrappedRepository.getAll()
            cache.clear()
            cachedIds.clear()
            for (each in items) {
                addToCache(each)
            }
            isSynced = true
            items
        }
    }

    override fun getByIds(ids: List<String>): List<T> {
        if (isSynced) {
            val items = mutableListOf<T>()
            for (each in ids) {
                if (cache.containsKey(each)) {
                    items.add(cache[each]!!)
                }
            }
            LOGGER.info("Retrieved all cached objects [" + items.size + "] with ids: " + ids)
            return items
        } else {
            val items = wrappedRepository.getByIds(ids)
            for (each in items) {
                addToCache(each)
            }
            return items
        }
    }

    private fun addToCache(each: T) {
        if (each.getId() == null) {
            throw UnexpectedException("Missing item id")
        }
        cache[each.getId()!!] = each
        cachedIds.add(each.getId()!!)
    }

    private fun removeFromCache(each: T) {
        if (each.getId() == null) {
            throw UnexpectedException("Missing item id")
        }
        cache.remove(each.getId()!!)
        cachedIds.remove(each.getId()!!)
    }

    override fun remove(id: String) {
        wrappedRepository.remove(id)
        cache.remove(id)
        cachedIds.remove(id)
    }

    override fun isEmpty(): Boolean {
        return if (isSynced) {
            cache.isEmpty()
        } else {
            val isEmpty = wrappedRepository.isEmpty()
            isSynced = isEmpty
            isEmpty
        }
    }

    override fun getSize(): Long {
        return if (isSynced) {
            cache.size.toLong()
        } else {
            val size = wrappedRepository.getSize()
            isSynced = size.equals(0)
            size
        }
    }

    override fun replaceAll(items: Collection<T>) {
        wrappedRepository.replaceAll(items)
        for (each in items) {
            addToCache(each)
        }
    }

    override fun getUniqueInstance(): T? {
        return if (cache.isNotEmpty() || isSynced) {
            if (cache.isEmpty()) null else cache.values.iterator().next()
        } else {
            val item = wrappedRepository.getUniqueInstance()
            if (item != null) {
                addToCache(item)
            }
            item
        }
    }

    fun clearCache() {
        cache.clear()
        cachedIds.clear()
        isSynced = false
    }
}
