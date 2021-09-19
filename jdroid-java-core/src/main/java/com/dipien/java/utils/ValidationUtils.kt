package com.dipien.java.utils

import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Pattern

/**
 * Validation Utils
 */
object ValidationUtils {

    val EMAIL_PATTERN: Pattern =
        Pattern.compile("^[_A-Za-z0-9\\+-]+(\\.[_A-Za-z0-9\\+-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)")

    /**
     * Validate the email address
     *
     * @param value The email address to validate
     * @return True if the value is a valid email address
     */
    fun isValidEmail(value: String): Boolean {
        return match(value, EMAIL_PATTERN)
    }

    private fun match(value: String, pattern: Pattern): Boolean {
        return value.isNotEmpty() && pattern.matcher(value).matches()
    }

    /**
     * Validate the URL
     *
     * @param value The URL to validate
     * @return True if the value is a valid URL
     */
    fun isValidURL(value: String): Boolean {
        try {
            URL(value)
            return true
        } catch (ex: MalformedURLException) {
            return false
        }
    }

    /**
     * Validate the URL without protocol
     *
     * @param value The URL to validate
     * @return True if the value is a valid URL
     */
    fun isValidURLWithoutProtocol(value: String): Boolean {
        return isValidURL("http://$value")
    }

    fun isValidDouble(number: String): Boolean {
        try {
            java.lang.Double.parseDouble(number)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
