package com.jdroid.java.utils

import com.jdroid.java.collections.Lists

import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

/**
 * Test for ValidationUtils
 */
class ValidationUtilsTest {

    /**
     * @return The different scenarios for test [ValidationUtils.isValidURL]
     */
    @DataProvider
    fun urlDataProvider(): Iterator<Array<Any>> {
        val cases = Lists.newArrayList<Array<Any>>()

        cases.add(arrayOf("http://www.validUrl.com", true))
        cases.add(arrayOf("http://www.validUrl.com/;kw=[service,110343]", true))
        cases.add(arrayOf("invalidUrl", false))
        cases.add(arrayOf("htp://invalid.com", false))

        return cases.iterator()
    }

    /**
     * @param value The Url value as String to Validate
     * @param expectedValue the expected result
     */
    @Test(dataProvider = "urlDataProvider")
    fun isValidURL(value: String, expectedValue: Boolean) {
        Assert.assertEquals(ValidationUtils.isValidURL(value), expectedValue)
    }
}
