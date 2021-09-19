package com.dipien.java.firebase.database.auth

import com.firebase.client.Firebase

abstract class CustomTokenFirebaseAuthenticationStrategy : FirebaseAuthenticationStrategy() {

    protected abstract val authToken: String

    override fun doAuthenticate(firebase: Firebase) {
        firebase.authWithCustomToken(authToken, this)
    }
}
