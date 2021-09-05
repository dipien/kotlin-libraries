package com.jdroid.java.firebase.database

import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.jdroid.java.domain.Entity
import com.jdroid.java.exception.UnexpectedException
import com.jdroid.java.firebase.database.auth.FirebaseAuthenticationStrategy
import com.jdroid.java.repository.Repository
import com.jdroid.java.logging.LoggerUtils
import java.util.HashMap

abstract class FirebaseDatabaseRepository<T : Entity> : Repository<T> {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(FirebaseDatabaseRepository::class.java)
    }

    private val firebaseAuthenticationStrategy: FirebaseAuthenticationStrategy? by lazy { createFirebaseAuthenticationStrategy() }

    protected abstract val firebaseUrl: String

    protected abstract val path: String

    protected abstract val entityClass: Class<T>

    protected open fun createFirebaseAuthenticationStrategy(): FirebaseAuthenticationStrategy? {
        return null
    }

    protected open fun createFirebase(): Firebase {
        var firebase = Firebase(firebaseUrl)
        if (firebase.auth == null) {
            firebaseAuthenticationStrategy?.authenticate(firebase)
        }
        firebase = firebase.child(path)
        return firebase
    }

    override operator fun get(id: String): T? {
        var firebase = createFirebase()
        firebase = firebase.child(id)
        val listener = FirebaseValueEventListener()
        firebase.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        val item = loadItem(listener.getDataSnapshot()!!)
        if (item != null) {
            LOGGER.debug("[$path] Retrieved object from database: [$item]")
        } else {
            LOGGER.debug("[$path] Object not found on database with id [$id]")
        }
        return item
    }

    override fun add(item: T) {
        var firebase = createFirebase()
        firebase = if (item.getId() != null) {
            firebase.child(item.getId()!!)
        } else {
            firebase.push()
        }

        val listener = FirebaseCompletionListener()
        firebase.setValue(item, listener)

        listener.waitOperation()
        if (item.getId() == null) {
            // Add the id field
            addIdField(firebase.key)
        }
        item.setId(firebase.key)
        LOGGER.debug("[$path] Stored object in database: $item")
    }

    private fun addIdField(id: String) {
        var firebase = createFirebase()
        firebase = firebase.child(id)

        val map = HashMap<String, Any>()
        map["id"] = id

        val listener = FirebaseCompletionListener()
        firebase.updateChildren(map, listener)
        listener.waitOperation()
    }

    override fun addAll(items: Collection<T>) {
        for (each in items) {
            add(each)
        }
    }

    override fun update(item: T) {
        if (item.getId() == null) {
            throw UnexpectedException("Item with null id can not be updated")
        }
        val firebase = createFirebase().child(item.getId()!!)

        val listener = FirebaseCompletionListener()
        firebase.setValue(item, listener)
        listener.waitOperation()
        item.setId(firebase.key)
        LOGGER.debug("[$path] Updated object in database: $item")
    }

    override fun getByField(fieldName: String, vararg values: Any): List<T> {
        val firebase = createFirebase()
        var query = firebase.orderByChild(fieldName)

        if (values.size > 1) {
            throw UnexpectedException("Just one value is supported")
        }
        val value = values[0]
        query = when (value) {
            is String -> query.equalTo(value)
            is Long -> query.equalTo(value.toDouble())
            is Double -> query.equalTo(value)
            is Int -> query.equalTo(value.toDouble())
            is Boolean -> query.equalTo(value)
            else -> throw UnexpectedException("Value type not supported")
        }

        val listener = FirebaseValueEventListener()
        query.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        val results = mutableListOf<T>()
        for (eachSnapshot in listener.getDataSnapshot()!!.children) {
            loadItem(eachSnapshot)?.let { results.add(it) }
        }
        LOGGER.debug("[" + path + "] Retrieved objects [" + results.size + "] from database with field [" + fieldName + "], value [" + value + "]")
        return results
    }

    override fun getAll(): List<T> {
        val firebase = createFirebase()
        val listener = FirebaseValueEventListener()
        firebase.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        val results = mutableListOf<T>()
        for (eachSnapshot in listener.getDataSnapshot()!!.children) {
            loadItem(eachSnapshot)?.let { results.add(it) }
        }
        LOGGER.debug("[" + path + "] Retrieved all objects [" + results.size + "]")
        return results
    }

    override fun getByIds(ids: List<String>): List<T> {
        val firebase = createFirebase()
        val listener = FirebaseValueEventListener()
        firebase.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        val results = mutableListOf<T>()
        for (eachSnapshot in listener.getDataSnapshot()!!.children) {
            val each = loadItem(eachSnapshot)
            if (each != null) {
                if (ids.contains(each.getId())) {
                    results.add(each)
                }
            }
        }
        LOGGER.debug("[" + path + "] Retrieved all objects [" + results.size + "] with ids: " + ids)
        return results
    }

    private fun loadItem(dataSnapshot: DataSnapshot): T? {
        val item = dataSnapshot.getValue(entityClass)
        if (item != null) {
            onItemLoaded(item)
        }
        return item
    }

    protected open fun onItemLoaded(item: T) {
        // Do nothing
    }

    override fun getItemByField(fieldName: String, vararg values: Any): T? {
        val items = getByField(fieldName, *values)
        return if (items.isNotEmpty()) {
            items[0]
        } else {
            null
        }
    }

    override fun remove(item: T) {
        remove(item.getId()!!)
    }

    override fun removeAll() {
        innerRemove(null)
    }

    override fun removeAll(items: Collection<T>) {
        for (each in items) {
            remove(each)
        }
    }

    override fun remove(id: String) {
        innerRemove(id)
    }

    private fun innerRemove(id: String?) {
        var firebase = createFirebase()
        if (id != null) {
            firebase = firebase.child(id)
        }

        val listener = FirebaseCompletionListener()
        firebase.removeValue(listener)
        listener.waitOperation()
        LOGGER.debug("[$path] Deleted object in database with id: $id")
    }

    override fun isEmpty(): Boolean {
        return getSize().equals(0)
    }

    override fun getSize(): Long {
        val firebase = createFirebase()
        val listener = FirebaseValueEventListener()
        firebase.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        return listener.getDataSnapshot()!!.childrenCount
    }

    override fun replaceAll(items: Collection<T>) {
        removeAll()
        addAll(items)
    }

    override fun getUniqueInstance(): T? {
        val results = getAll()
        return if (results.isNotEmpty()) {
            results[0]
        } else null
    }
}
