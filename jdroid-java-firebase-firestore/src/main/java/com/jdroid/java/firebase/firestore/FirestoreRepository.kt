package com.jdroid.java.firebase.firestore

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.FieldValue
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.Query
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
import com.google.firebase.FirebaseApp
import com.google.firebase.cloud.FirestoreClient
import com.jdroid.java.exception.UnexpectedException
import com.jdroid.java.firebase.admin.FirebaseAdminSdkHelper
import com.jdroid.java.logging.LoggerUtils
import com.jdroid.java.repository.Entity
import com.jdroid.java.repository.Repository
import java.util.HashMap
import java.util.concurrent.ExecutionException

abstract class FirestoreRepository<T : Entity>(val firestoreServiceAccountPath: String? = null) : Repository<T> {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(FirestoreRepository::class.java)

        private const val ADD_BATCH_LIMIT = 500
        private const val DELETE_BATCH_SIZE = 100
    }

    protected abstract fun getPath(): String

    protected abstract fun getEntityClass(): Class<T>

    protected open fun getOrderByField(): String? {
        return null
    }

    protected open fun getOrderByDirection(): Query.Direction {
        return Query.Direction.ASCENDING
    }

    protected open fun getLimit(): Int? {
        return null
    }

    protected open fun getDeleteBatchSize(): Int {
        return DELETE_BATCH_SIZE
    }

    protected open fun createFirestore(): Firestore {
        if (firestoreServiceAccountPath != null && FirebaseApp.getApps().isEmpty()) {
            FirebaseAdminSdkHelper.init(firestoreServiceAccountPath)
        }
        return FirestoreClient.getFirestore()
    }

    protected open fun createCollectionReference(): CollectionReference {
        return createCollectionReference(createFirestore())
    }

    private fun createCollectionReference(firestore: Firestore): CollectionReference {
        return firestore.collection(getPath())
    }

    override fun add(item: T) {
        val collectionReference = createCollectionReference()
        val documentReference: DocumentReference
        if (item.getId() != null) {
            documentReference = collectionReference.document(item.getId()!!)
        } else {
            documentReference = collectionReference.document()
            item.setId(documentReference.id)
        }

        getWriteResult(documentReference.set(item))

        LOGGER.debug("[${getPath()}] Stored object in database: $item")
    }

    override fun addAll(items: Collection<T>) {
        val firestore = createFirestore()
        var pendingItems = mutableListOf<T>(items)
        while (!pendingItems.isEmpty()) {
            processBatch(firestore, pendingItems.subList(0, Math.min(pendingItems.size, ADD_BATCH_LIMIT)))
            if (pendingItems.size > ADD_BATCH_LIMIT) {
                pendingItems = pendingItems.subList(ADD_BATCH_LIMIT, pendingItems.size)
            } else {
                break
            }
        }

        LOGGER.debug("[${getPath()}] Stored objects in database: $items")
    }

    private fun processBatch(firestore: Firestore, items: Collection<T>) {
        val batch = firestore.batch()
        val collectionReference = createCollectionReference(firestore)

        for (item in items) {
            val documentReference: DocumentReference
            if (item.getId() != null) {
                documentReference = collectionReference.document(item.getId()!!)
            } else {
                documentReference = collectionReference.document()
                item.setId(documentReference.id)
            }

            batch.set(documentReference, item)
        }

        try {
            batch.commit().get()
        } catch (e: InterruptedException) {
            throw UnexpectedException(e)
        } catch (e: ExecutionException) {
            throw UnexpectedException(e)
        }
    }

    override fun update(item: T) {
        if (item.getId() == null) {
            throw UnexpectedException("Item with null id can not be updated")
        }

        val collectionReference = createCollectionReference()
        val documentReference = collectionReference.document(item.getId()!!)

        getWriteResult(documentReference.set(item))

        LOGGER.debug("[${getPath()}] Updated object in database: $item")
    }

    override operator fun get(id: String): T? {
        val documentSnapshot = getDocumentSnapshot(createCollectionReference().document(id).get())
        var item: T? = null
        if (documentSnapshot.exists()) {
            item = getItem(documentSnapshot)
            if (item!!.getId() == null) {
                item.setId(id)
            }
            LOGGER.debug("[${getPath()}] Retrieved object from database: [$item]")
        } else {
            LOGGER.debug("[${getPath()}] Object not found on database with id [$id]")
        }
        return item
    }

    override fun getByField(fieldName: String, vararg values: Any): List<T> {
        val results = mutableListOf<T>()
        val collectionReference = createCollectionReference()
        for (value in values) {
            var query = collectionReference.whereEqualTo(fieldName, value)
            if (getOrderByField() != null) {
                query = query.orderBy(getOrderByField()!!, getOrderByDirection())
            }
            if (getLimit() != null) {
                query = query.limit(getLimit()!!)
            }
            for (documentSnapshot in getQuerySnapshot(query.get()).documents) {
                val item = getItem(documentSnapshot)
                if (item!!.getId() == null) {
                    item.setId(documentSnapshot.id)
                }
                results.add(item)
            }
        }

        LOGGER.debug("[${getPath()}] Retrieved objects [" + results.size + "] from database with field [" + fieldName + "], values [" + values + "]")

        return results
    }

    protected fun getItemByQuery(query: Query): T? {
        val items = getByQuery(query)
        return if (items.isNotEmpty()) {
            items[0]
        } else {
            null
        }
    }

    protected fun getByQuery(query: Query): List<T> {
        val results = mutableListOf<T>()
        for (documentSnapshot in getQuerySnapshot(query.get()).documents) {
            val item = getItem(documentSnapshot)
            if (item!!.getId() == null) {
                item.setId(documentSnapshot.id)
            }
            results.add(item)
        }

        LOGGER.debug("[${getPath()}] Retrieved objects [" + results.size + "] from database with query [" + query + "]")

        return results
    }

    override fun getAll(): List<T> {
        val results = mutableListOf<T>()
        var collectionReference: Query = createCollectionReference()
        if (getOrderByField() != null) {
            collectionReference = collectionReference.orderBy(getOrderByField()!!, getOrderByDirection())
        }
        if (getLimit() != null) {
            collectionReference = collectionReference.limit(getLimit()!!)
        }
        for (documentSnapshot in getQuerySnapshot(collectionReference.get()).documents) {
            val item = getItem(documentSnapshot)
            if (item!!.getId() == null) {
                item.setId(documentSnapshot.id)
            }
            results.add(item)
        }
        LOGGER.debug("[${getPath()}] Retrieved all objects [" + results.size + "]")
        return results
    }

    override fun getByIds(ids: List<String>): List<T> {
        return getByField("id", *ids.toTypedArray())
    }

    override fun getItemByField(fieldName: String, vararg values: Any): T? {
        val items = getByField(fieldName, *values)
        return if (items.isNotEmpty()) {
            items[0]
        } else {
            null
        }
    }

    override fun getUniqueInstance(): T? {
        val documentSnapshots = getQuerySnapshot(createCollectionReference().limit(1).get()).documents
        if (documentSnapshots.isNotEmpty()) {
            val documentSnapshot = documentSnapshots[0]
            val item = getItem(documentSnapshot)
            if (item!!.getId() == null) {
                item.setId(documentSnapshot.id)
            }
            return item
        }
        return null
    }

    private fun getItem(documentSnapshot: DocumentSnapshot): T? {
        val item = documentSnapshot.toObject(getEntityClass())
        onItemLoaded(item)
        return item
    }

    protected fun onItemLoaded(item: T?) {
        // Do nothing
    }

    override fun remove(item: T) {
        remove(item.getId()!!)
    }

    override fun removeAll() {
        deleteCollection(createCollectionReference(), getDeleteBatchSize())
        LOGGER.debug("[${getPath()}] Deleted all objects in database")
    }

    /**
     * Delete a collection in batches to avoid out-of-memory errors.
     * Batch size may be tuned based on document size (atmost 1MB) and application requirements.
     */
    private fun deleteCollection(collectionReference: CollectionReference, batchSize: Int) {
        // retrieve a small batch of documents to avoid out-of-memory errors
        val future = collectionReference.limit(batchSize).get()
        var deleted = 0
        for (document in getQuerySnapshot(future).documents) {
            document.reference.delete()
            ++deleted
        }
        if (deleted >= batchSize) {
            // retrieve and delete another batch
            deleteCollection(collectionReference, batchSize)
        }
    }

    override fun removeAll(items: Collection<T>) {
        for (item in items) {
            remove(item)
        }
    }

    override fun remove(id: String) {
        getWriteResult(createCollectionReference().document(id).delete())
        LOGGER.debug("[${getPath()}] Deleted object in database with id: $id")
    }

    override fun isEmpty(): Boolean {
        return getQuerySnapshot(createCollectionReference().limit(1).get()).documents.isEmpty()
    }

    override fun getSize(): Long {
        return getAll().size.toLong()
    }

    override fun replaceAll(items: Collection<T>) {
        removeAll()
        addAll(items)
    }

    fun removeFields(id: String, vararg fieldNames: String) {
        val collectionReference = createCollectionReference()
        val documentReference = collectionReference.document(id)
        val updates = HashMap<String, Any>()
        for (fieldName in fieldNames) {
            updates[fieldName] = FieldValue.delete()
        }
        getWriteResult(documentReference.update(updates))
    }

    private fun getQuerySnapshot(futureResult: ApiFuture<QuerySnapshot>): QuerySnapshot {
        try {
            return futureResult.get()
        } catch (e: InterruptedException) {
            throw UnexpectedException(e)
        } catch (e: ExecutionException) {
            throw UnexpectedException(e)
        }
    }

    private fun getDocumentSnapshot(futureResult: ApiFuture<DocumentSnapshot>): DocumentSnapshot {
        try {
            return futureResult.get()
        } catch (e: InterruptedException) {
            throw UnexpectedException(e)
        } catch (e: ExecutionException) {
            throw UnexpectedException(e)
        }
    }

    private fun getWriteResult(futureResult: ApiFuture<WriteResult>): WriteResult {
        try {
            return futureResult.get()
        } catch (e: InterruptedException) {
            throw UnexpectedException(e)
        } catch (e: ExecutionException) {
            throw UnexpectedException(e)
        }
    }
}
