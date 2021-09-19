package com.dipien.core.utils

import com.dipien.core.exception.UnexpectedException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

enum class Hasher private constructor(private val algorithm: String) {

    SHA_1("SHA-1"),
    SHA_512("SHA-512");

    fun isSupported(): Boolean {
        try {
            MessageDigest.getInstance(algorithm)
            return true
        } catch (e: NoSuchAlgorithmException) {
            return false
        }
    }

    /**
     * Algorithm that returns a Hashed string of the value given as parameter
     *
     * @param value the string to hash
     * @return String hashed value.
     */
    fun hash(value: String): String {
        try {
            val messageDigest = MessageDigest.getInstance(algorithm)
            messageDigest.reset()
            messageDigest.update(value.toByteArray())
            val hashed = messageDigest.digest()
            return EncryptionUtils.toHex(hashed)
        } catch (e: NoSuchAlgorithmException) {
            throw UnexpectedException(e)
        }
    }

    companion object {

        fun getSupportedHasher(): Hasher {
            return if (SHA_512.isSupported()) {
                SHA_512
            } else {
                SHA_1
            }
        }
    }
}
