package com.jdroid.java.firebase.database

import com.firebase.client.Firebase
import com.jdroid.java.exception.UnexpectedException
import com.jdroid.java.firebase.database.auth.FirebaseAuthenticationStrategy
import com.jdroid.java.repository.Pair
import com.jdroid.java.repository.PairRepository
import com.jdroid.java.utils.LoggerUtils

abstract class PairFirebaseRepository : PairRepository {

    private val firebaseAuthenticationStrategy: FirebaseAuthenticationStrategy?

    protected abstract val firebaseUrl: String

    protected abstract val path: String

    init {
        firebaseAuthenticationStrategy = createFirebaseAuthenticationStrategy()
    }

    protected open fun createFirebaseAuthenticationStrategy(): FirebaseAuthenticationStrategy? {
        return null
    }

    protected open fun createFirebase(): Firebase {
        var firebase = Firebase(firebaseUrl)
        if (firebaseAuthenticationStrategy != null && firebase.auth == null) {
            firebaseAuthenticationStrategy.authenticate(firebase)
        }
        firebase = firebase.child(path)
        return firebase
    }

    override operator fun get(id: String): Pair? {
        var firebase = createFirebase()
        firebase = firebase.child(id)
        val listener = FirebaseValueEventListener()
        firebase.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        val result = listener.getDataSnapshot()!!.getValue(String::class.java)
        var pair: Pair? = null
        if (result != null) {
            pair = Pair()
            pair.setId(id)
            pair.value = result
            LOGGER.debug("Retrieved object from database with path [ $path]. [ $result ]")
        } else {
            LOGGER.debug("Object not found on database with path [ $path ] and id [ $id ]")
        }
        return pair
    }

    override fun add(item: Pair) {
        var firebase = createFirebase()
        firebase = firebase.child(item.getId()!!)

        val listener = FirebaseCompletionListener()
        firebase.setValue(item.value, listener)

        listener.waitOperation()
        LOGGER.debug("Stored object in database: $item")
    }

    override fun addAll(items: Collection<Pair>) {
        for (each in items) {
            add(each)
        }
    }

    override fun update(item: Pair) {
        if (item.getId() == null) {
            throw UnexpectedException("Item with null id can not be updated")
        }

        var firebase = createFirebase()
        firebase = firebase.child(item.getId()!!)

        val listener = FirebaseCompletionListener()
        firebase.setValue(item.value, listener)

        listener.waitOperation()
        LOGGER.debug("Updated object in database: $item")
    }

    override fun getByField(fieldName: String, vararg values: Any): List<Pair> {
        throw UnsupportedOperationException()
    }

    override fun getItemByField(fieldName: String, vararg values: Any): Pair? {
        throw UnsupportedOperationException()
    }

    override fun getAll(): List<Pair> {
        val firebase = createFirebase()
        val listener = FirebaseValueEventListener()
        firebase.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        val results = mutableListOf<Pair>()
        for (eachSnapshot in listener.getDataSnapshot()!!.children) {
            val pair = Pair()
            pair.setId(eachSnapshot.key)
            pair.value = eachSnapshot.getValue(String::class.java)
            results.add(pair)
        }
        LOGGER.debug("Retrieved all objects [" + results.size + "] from path: " + path)
        return results
    }

    override fun getByIds(ids: List<String>): List<Pair> {
        val firebase = createFirebase()
        val listener = FirebaseValueEventListener()
        firebase.addListenerForSingleValueEvent(listener)
        listener.waitOperation()
        val results = mutableListOf<Pair>()
        for (eachSnapshot in listener.getDataSnapshot()!!.children) {
            val pair = Pair()
            pair.setId(eachSnapshot.key)
            pair.value = eachSnapshot.getValue(String::class.java)
            if (ids.contains(pair.getId())) {
                results.add(pair)
            }
        }
        LOGGER.debug("Retrieved all objects [" + results.size + "] from path: " + path + " and ids: " + ids)
        return results
    }

    override fun remove(item: Pair) {
        remove(item.getId()!!)
    }

    override fun removeAll() {
        innerRemove(null)
    }

    override fun removeAll(items: Collection<Pair>) {
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
        LOGGER.debug("Deleted object in database: with id: " + id!!)
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

    override fun replaceAll(items: Collection<Pair>) {
        for (each in items) {
            update(each)
        }
    }

    override fun getUniqueInstance(): Pair? {
        val results = getAll()
        return if (results.isNotEmpty()) {
            results[0]
        } else null
    }

    companion object {

        private val LOGGER = LoggerUtils.getLogger(PairFirebaseRepository::class.java)
    }
}
