package com.dipien.java.utils

import com.dipien.java.Assert
import com.dipien.java.collections.Sets
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StringUtilsTest {

    @Test
    fun getFirstToken() {
        assertEquals("hola", StringUtils.getFirstToken("hola,como,estas"))
        assertEquals("hola", StringUtils.getFirstToken("hola;como;estas", ";"))
    }

    @Test
    fun getNotEmptyString() {
        assertEquals(null, StringUtils.getNotEmptyString(""), null)
        assertEquals("holi", StringUtils.getNotEmptyString("holi"))
    }

    @Test
    fun hasOnlyCharacters() {
        assertTrue(StringUtils.hasOnlyCharacters("asdqewzcxzxcqeasdr"))
        assertFalse(StringUtils.hasOnlyCharacters("asdqewzcxzxcq2easdr"))
    }

    @Test
    fun removeNonAlphanumericCharacters() {
        assertEquals("12sasd", StringUtils.toAlphanumeric("12sa'sd"))
        assertEquals("sasd12", StringUtils.toAlphanumeric("sasd12"))
    }

    @Test
    fun toAlphanumeric() {
        toAlphanumeric("text", "text")
        toAlphanumeric("text.", "text")
        toAlphanumeric("text.\\", "text")
        toAlphanumeric("?text.\\", "text")
        toAlphanumeric(" ?text.\\", " text")
        toAlphanumeric("text.\\text2", "texttext2")
        toAlphanumeric("text.\\text2??", "texttext2")
        toAlphanumeric("text.\\text2??text3", "texttext2text3")
        toAlphanumeric("text .\\text2 ??text3", "text text2 text3")
        toAlphanumeric("text text2 text3", "text text2 text3")
        toAlphanumeric("text  text2", "text  text2")
    }

    private fun toAlphanumeric(text: String, expectedText: String) {
        assertEquals(expectedText, StringUtils.toAlphanumeric(text))
    }

    @Test
    fun truncate() {
        truncate(null, 5, null, null)
        truncate("", 5, "", "")
        truncate("text1", 1, "t", "")
        truncate("text1", 2, "te", "")
        truncate("text1", 3, "tex", "")
        truncate("text1", 4, "t...", "")
        truncate("text1", 5, "text1", "text1")
        truncate("text1", 6, "text1", "text1")
        truncate("text1", 10, "text1", "text1")
        truncate("text1 text2", 4, "t...", "")
        truncate("text1 text2", 5, "te...", "text1")
        truncate("text1 text2", 6, "tex...", "text1")
        truncate("text1 text2", 7, "text...", "text1")
        truncate("text1 text2", 11, "text1 text2", "text1 text2")
        truncate("text1 text2 ", 11, "text1 te...", "text1 text2")
        truncate("text1  text2 ", 11, "text1  t...", "text1 ")
    }

    /**
     * @param text The text to truncate
     * @param maxCharacters The maximum amount of characters allowed
     * @param expectedWordsTruncatedText The expected truncated text with words truncated
     * @param expectedWordsNotTruncatedText The expected truncated text without words truncated
     */
    private fun truncate(
        text: String?,
        maxCharacters: Int?,
        expectedWordsTruncatedText: String?,
        expectedWordsNotTruncatedText: String?
    ) {
        assertEquals(expectedWordsTruncatedText, StringUtils.truncate(text, maxCharacters))
        assertEquals(expectedWordsTruncatedText, StringUtils.truncate(text, maxCharacters, true))
        assertEquals(expectedWordsNotTruncatedText, StringUtils.truncate(text, maxCharacters, false))
    }

    @Test
    fun extractPlaceholders() {
        extractPlaceholders("", hashSetOf())
        extractPlaceholders("a", hashSetOf())
        extractPlaceholders("\${a}", Sets.newHashSet("a"))
        extractPlaceholders("\${a}\${a}", Sets.newHashSet("a"))
        extractPlaceholders("\${a}\${b}", Sets.newHashSet("a", "b"))
    }

    /**
     * Verifies the [StringUtils.extractPlaceHolders] method
     *
     * @param string The string
     * @param placeholderNames The placeholder names
     */
    private fun extractPlaceholders(string: String, placeholderNames: Set<String>) {
        val result = StringUtils.extractPlaceHolders(string)
        Assert.assertEqualsNoOrder(result, placeholderNames)
    }

    @Test
    fun wordWrapToTwoLinesTests() {
        // Word wrap with two words of the same size
        assertEquals(
            StringUtils.wordWrapToTwoLines("Aerolineas Argentinas", 10),
            "Aerolineas\nArgentinas"
        )
        // Word wrap with the first word longer than the second
        assertEquals("Southern\nWinds", StringUtils.wordWrapToTwoLines("Southern Winds", 10))
        // Word wrap with the first word shorter than the second
        assertEquals("Virgin\nAirlines", StringUtils.wordWrapToTwoLines("Virgin Airlines", 10))
        // Without Word wrap, text length less than the minimum
        assertEquals("Swiss Air", StringUtils.wordWrapToTwoLines("Swiss Air", 10))
        // Without Word wrap, text length less than minimun and one word
        assertEquals("Tam", StringUtils.wordWrapToTwoLines("Tam", 10))
        // Without Word wrap, text length greater than minimun and one word
        assertEquals("LargeNameAirline", StringUtils.wordWrapToTwoLines("LargeNameAirline", 10))
        // Word wrap with more than two words
        assertEquals("Large Name\nAirline", StringUtils.wordWrapToTwoLines("Large Name Airline", 10))
    }

    @Test
    fun hasOnlyCharacterTest() {
        assertTrue(StringUtils.hasOnlyCharacters("Jhon"))
        assertTrue(StringUtils.hasOnlyCharacters("Jhon Wayne"))
        assertTrue(StringUtils.hasOnlyCharacters(" Jhon  Nicholas   Wayne     "))
        assertFalse(StringUtils.hasOnlyCharacters("Jhon2"))
        assertFalse(StringUtils.hasOnlyCharacters("Jhon 5"))
        assertFalse(StringUtils.hasOnlyCharacters("5Jhon "))
        assertFalse(StringUtils.hasOnlyCharacters("Jhon Wayne the 3rd"))
        assertFalse(StringUtils.hasOnlyCharacters("Jhon Wayne the 3rd."))
        assertFalse(StringUtils.hasOnlyCharacters("Jhon Wayne the 3rd _ "))
    }
}
