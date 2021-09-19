package com.dipien.firebase.database

import com.firebase.client.FirebaseError

class FirebaseException(firebaseError: FirebaseError) : RuntimeException(firebaseError.message)
