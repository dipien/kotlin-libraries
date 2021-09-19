package com.dipien.firebase.database

import com.firebase.client.DataSnapshot

import java.util.concurrent.CountDownLatch

class FirebaseCountDownLatch : CountDownLatch(1) {

    var dataSnapshot: DataSnapshot? = null
    var firebaseException: FirebaseException? = null
}
