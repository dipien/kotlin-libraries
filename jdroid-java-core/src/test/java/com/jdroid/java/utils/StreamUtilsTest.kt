package com.jdroid.java.utils

import org.testng.Assert.assertTrue
import org.testng.AssertJUnit.assertFalse
import org.testng.annotations.Test
import java.io.ByteArrayInputStream

class StreamUtilsTest {

    @Test
    fun isEquals() {
        val inputStream1 = ByteArrayInputStream("123".toByteArray())
        val inputStream2 = ByteArrayInputStream("123".toByteArray())
        val inputStream3 = ByteArrayInputStream("124".toByteArray())

        assertTrue(StreamUtils.isEquals(inputStream1, inputStream2))
        assertFalse(StreamUtils.isEquals(inputStream1, inputStream3))
    }
}
