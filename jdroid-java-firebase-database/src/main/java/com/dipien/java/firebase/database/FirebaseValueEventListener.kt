package com.dipien.java.firebase.database

import com.firebase.client.DataSnapshot
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener

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
            throw RuntimeException(e)
        }
    }

    fun getDataSnapshot(): DataSnapshot? {
        return done.dataSnapshot
    }
}
