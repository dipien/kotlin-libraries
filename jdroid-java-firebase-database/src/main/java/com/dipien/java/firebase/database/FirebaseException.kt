package com.dipien.java.firebase.database

import com.firebase.client.FirebaseError

class FirebaseException(firebaseError: FirebaseError) : RuntimeException(firebaseError.message)
