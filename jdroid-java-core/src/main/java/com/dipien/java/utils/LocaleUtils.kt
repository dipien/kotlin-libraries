package com.dipien.java.utils

import java.util.Locale

object LocaleUtils {

    fun getAcceptLanguage(): String {
        var language = Locale.getDefault().language
        val country = Locale.getDefault().country
        if (country.isNotBlank()) {
            language = "$language-$country"
        }
        return language
    }

    fun getLanguage(acceptLanguage: String): String {
        return acceptLanguage.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }
}
