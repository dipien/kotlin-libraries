package com.jdroid.java.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class SanitizerTest {

    @Test
    fun plainString() {

        // removingAccentsInLowercase
        plainString("bcdfghjklmnñpqrstvwxyz", "bcdfghjklmnnpqrstvwxyz")
        plainString("áéíóú", "aeiou")
        plainString("äëïöü", "aeiou")
        plainString("àèìòù", "aeiou")
        plainString("âêîôû", "aeiou")
        plainString("ãõ", "ao")
        plainString("å", "a")

        // removingAccentsInUppercase
        plainString("BCDFGHJKLMNÑPQRSTVWXYZ", "BCDFGHJKLMNNPQRSTVWXYZ")
        plainString("ÁÉÍÓÚ", "AEIOU")
        plainString("ÄËÏÖÜ", "AEIOU")
        plainString("ÀÈÌÒÙ", "AEIOU")
        plainString("ÂÊÎÔÛ", "AEIOU")
        plainString("ÃÕ", "AO")
        plainString("Å", "A")
    }

    private fun plainString(value: String, expected: String) {
        assertEquals(expected, Sanitizer.plainString(value))
    }

    @Test
    fun removingNumbers() {
        assertEquals("BCDFGHJKLMNNPQRSTVWXYZ", Sanitizer.plainStringWithoutNumbers("1213123BCDF7G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"))
    }

    @Test
    fun removingExtraSpaces() {
        assertEquals("B C DF GH JKL MNNP QRST VWXY Z", Sanitizer.plainStringWithoutExtraSpaces(" B     C DF GH JKL MNÑP QRST VWXY Z "))
    }

    @Test
    fun removeNumbers() {
        assertEquals("BCDFGHJKLMNÑPQRSTVWXYZ", Sanitizer.removeNumbers("5BCDF5G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"))
    }

    @Test
    fun removingNonNumbers() {
        assertEquals("55654312888", Sanitizer.justNumbers("5BCDF5G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"))
    }
}
