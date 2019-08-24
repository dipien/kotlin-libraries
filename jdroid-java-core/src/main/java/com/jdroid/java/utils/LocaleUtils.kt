package com.jdroid.java.utils

import java.util.Locale

object LocaleUtils {

    val acceptLanguage: String
        get() {
            var language = Locale.getDefault().language
            val country = Locale.getDefault().country
            if (StringUtils.isNotBlank(country)) {
                language = "$language-$country"
            }
            return language
        }

    fun getLanguage(acceptLanguage: String): String {
        return acceptLanguage.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }
}
