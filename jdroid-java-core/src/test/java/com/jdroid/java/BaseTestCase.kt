package com.jdroid.java

import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod

/**
 * Base TestCase class
 */
open class BaseTestCase {

    /**
     * Method executed before the class is being tested.
     *
     * @throws Exception The exception by configuration
     */
    @BeforeClass
    @Throws(Exception::class)
    protected fun initialize() {
        onInit()
    }

    /**
     * Override this method to define the behavior to be executed before the class is being tested.
     *
     * @throws Exception The exception by configuration
     */
    @Throws(Exception::class)
    protected open fun onInit() {
        // Do nothing
    }

    /**
     * Method executed before each test.
     *
     * @throws Exception The exception by configuration
     */
    @BeforeMethod
    @Throws(Exception::class)
    protected fun setUp() {
        onSetUp()
    }

    /**
     * Override this method to define the behavior to be executed before each test.
     *
     * @throws Exception The exception by configuration
     */
    @Throws(Exception::class)
    protected open fun onSetUp() {
        // Do nothing
    }

    /**
     * Method executed after the class is being tested.
     *
     * @throws Exception The exception by configuration
     */
    @AfterMethod
    @Throws(Exception::class)
    protected fun tearDown() {
        onTearDown()
    }

    /**
     * Override this method to define the behavior to be executed after each test.
     *
     * @throws Exception The exception by configuration
     */
    @Throws(Exception::class)
    protected open fun onTearDown() {
        // Do nothing
    }

    /**
     * Method executed after the class is being tested.
     *
     * @throws Exception The exception by configuration
     */
    @AfterClass
    @Throws(Exception::class)
    protected fun end() {
        onEnd()
    }

    /**
     * Override this method to define the behavior to be executed after the class is being tested.
     *
     * @throws Exception The exception by configuration
     */
    @Throws(Exception::class)
    protected open fun onEnd() {
        // Do nothing
    }
}
