package com.jdroid.java.utils

import com.jdroid.java.collections.Lists
import org.testng.Assert.assertEquals
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class SanitizerTest {

    @DataProvider
    fun plainStringDataProvider(): Iterator<Array<Any>> {
        val cases = Lists.newArrayList<Array<Any>>()

        // removingAccentsInLowercase
        cases.add(arrayOf("bcdfghjklmnñpqrstvwxyz", "bcdfghjklmnnpqrstvwxyz"))
        cases.add(arrayOf("áéíóú", "aeiou"))
        cases.add(arrayOf("äëïöü", "aeiou"))
        cases.add(arrayOf("àèìòù", "aeiou"))
        cases.add(arrayOf("âêîôû", "aeiou"))
        cases.add(arrayOf("ãõ", "ao"))
        cases.add(arrayOf("å", "a"))

        // removingAccentsInUppercase
        cases.add(arrayOf("BCDFGHJKLMNÑPQRSTVWXYZ", "BCDFGHJKLMNNPQRSTVWXYZ"))
        cases.add(arrayOf("ÁÉÍÓÚ", "AEIOU"))
        cases.add(arrayOf("ÄËÏÖÜ", "AEIOU"))
        cases.add(arrayOf("ÀÈÌÒÙ", "AEIOU"))
        cases.add(arrayOf("ÂÊÎÔÛ", "AEIOU"))
        cases.add(arrayOf("ÃÕ", "AO"))
        cases.add(arrayOf("Å", "A"))

        return cases.iterator()
    }

    @Test(dataProvider = "plainStringDataProvider")
    fun plainString(value: String, expected: String) {
        assertEquals(Sanitizer.plainString(value), expected)
    }

    @Test
    fun removingNumbers() {
        assertEquals(
            Sanitizer.plainStringWithoutNumbers("1213123BCDF7G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"),
            "BCDFGHJKLMNNPQRSTVWXYZ"
        )
    }

    @Test
    fun removingExtraSpaces() {
        assertEquals(
            Sanitizer.plainStringWithoutExtraSpaces(" B     C DF GH JKL MNÑP QRST VWXY Z "),
            "B C DF GH JKL MNNP QRST VWXY Z"
        )
    }

    @Test
    fun removeNumbers() {
        assertEquals(Sanitizer.removeNumbers("5BCDF5G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"), "BCDFGHJKLMNÑPQRSTVWXYZ")
    }

    @Test
    fun removingNonNumbers() {
        assertEquals(Sanitizer.justNumbers("5BCDF5G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"), "55654312888")
    }
}
