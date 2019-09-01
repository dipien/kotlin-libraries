package com.jdroid.java.utils

import java.text.Normalizer

object Sanitizer {

    fun plainString(text: String?): String? {
        var plain: String? = null
        if (text != null) {
            val nfdNormalizedString = Normalizer.normalize(text, Normalizer.Form.NFD)
            plain = nfdNormalizedString.replace("[^a-zA-Z0-9\\s]".toRegex(), "")
            plain = plain.replace("\\s{2,}".toRegex(), " ").trim { it <= ' ' }
        }
        return plain
    }

    fun plainStringWithoutExtraSpaces(text: String?): String? {
        var plain = plainString(text)
        if (text != null) {
            plain = plain!!.replace("\\s{2,}".toRegex(), " ").trim { it <= ' ' }
        }
        return plain
    }

    fun plainStringWithoutNumbers(text: String): String? {
        val withoutNumbers = removeNumbers(text)
        return plainString(withoutNumbers)
    }

    fun justNumbers(text: String?): String? {
        return text?.replace("[^\\d]".toRegex(), "")
    }

    fun removeNumbers(text: String?): String? {
        return text?.replace("\\d".toRegex(), "")
    }

    fun removeDashes(text: String?): String? {
        return text?.replace(StringUtils.DASH.toRegex(), "")
    }
}
