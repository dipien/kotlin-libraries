package com.jdroid.java.firebase.database

import com.firebase.client.FirebaseError
import com.jdroid.java.exception.AbstractException

class FirebaseException(val firebaseError: FirebaseError) : AbstractException(firebaseError.message)
