package com.jdroid.java.utils

import org.junit.Assert
import org.junit.Test

/**
 * Test for ValidationUtils
 */
class ValidationUtilsTest {

    @Test
    fun isValidURL() {
        Assert.assertTrue(ValidationUtils.isValidURL("http://www.validUrl.com"))
        Assert.assertTrue(ValidationUtils.isValidURL("http://www.validUrl.com/;kw=[service,110343]"))
        Assert.assertFalse(ValidationUtils.isValidURL("invalidUrl"))
        Assert.assertFalse(ValidationUtils.isValidURL("htp://invalid.com"))
    }
}
