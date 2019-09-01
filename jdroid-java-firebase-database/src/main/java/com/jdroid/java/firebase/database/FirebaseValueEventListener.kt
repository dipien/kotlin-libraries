package com.jdroid.java.firebase.database

import com.firebase.client.DataSnapshot
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.jdroid.java.exception.UnexpectedException

class FirebaseValueEventListener : ValueEventListener {

    private val done = FirebaseCountDownLatch()

    override fun onDataChange(snapshot: DataSnapshot) {
        done.dataSnapshot = snapshot
        done.countDown()
    }

    override fun onCancelled(firebaseError: FirebaseError) {
        done.firebaseException = FirebaseException(firebaseError)
        done.countDown()
    }

    fun waitOperation() {
        try {
            done.await()
            done.firebaseException?.let {
                throw it
            }
        } catch (e: InterruptedException) {
            throw UnexpectedException(e)
        }
    }

    fun getDataSnapshot(): DataSnapshot? {
        return done.dataSnapshot
    }
}
