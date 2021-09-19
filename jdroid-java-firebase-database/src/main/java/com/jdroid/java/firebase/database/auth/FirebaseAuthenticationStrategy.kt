package com.jdroid.java.firebase.database.auth

import com.firebase.client.AuthData
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.jdroid.java.firebase.database.FirebaseDatabaseRepository
import com.jdroid.java.logging.LoggerUtils
import java.util.concurrent.CountDownLatch

abstract class FirebaseAuthenticationStrategy : Firebase.AuthResultHandler {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(FirebaseDatabaseRepository::class.java)
    }

    lateinit var countDownLatch: CountDownLatch
        private set

    fun authenticate(firebase: Firebase) {
        countDownLatch = CountDownLatch(1)
        doAuthenticate(firebase)
        try {
            countDownLatch.await()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    protected abstract fun doAuthenticate(firebase: Firebase)

    protected fun doOnAuthenticationError(error: FirebaseError) {
        throw error.toException()
    }

    protected fun doOnAuthenticated(authData: AuthData) {
        // Do nothing
    }

    override fun onAuthenticationError(error: FirebaseError) {
        countDownLatch.countDown()
        doOnAuthenticationError(error)
    }

    override fun onAuthenticated(authData: AuthData) {
        countDownLatch.countDown()
        LOGGER.info("Firebase authenticated. $authData")
        doOnAuthenticated(authData)
    }
}
