package com.jdroid.java;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base TestCase class
 */
public class BaseTestCase {
   
   /**
    * Method executed before the class is being tested.
    * 
    * @throws Exception The exception by configuration
    */
   @BeforeClass
   protected final void initialize() throws Exception {
      onInit();
   }
   
   /**
    * Override this method to define the behavior to be executed before the class is being tested.
    * 
    * @throws Exception The exception by configuration
    */
   protected void onInit() throws Exception {
      // Do nothing
   }
   
   /**
    * Method executed before each test.
    * 
    * @throws Exception The exception by configuration
    */
   @BeforeMethod
   protected final void setUp() throws Exception {
      onSetUp();
   }
   
   /**
    * Override this method to define the behavior to be executed before each test.
    * 
    * @throws Exception The exception by configuration
    */
   protected void onSetUp() throws Exception {
      // Do nothing
   }
   
   /**
    * Method executed after the class is being tested.
    * 
    * @throws Exception The exception by configuration
    */
   @AfterMethod
   protected final void tearDown() throws Exception {
      onTearDown();
   }
   
   /**
    * Override this method to define the behavior to be executed after each test.
    * 
    * @throws Exception The exception by configuration
    */
   protected void onTearDown() throws Exception {
      // Do nothing
   }
   
   /**
    * Method executed after the class is being tested.
    * 
    * @throws Exception The exception by configuration
    */
   @AfterClass
   protected final void end() throws Exception {
      onEnd();
   }
   
   /**
    * Override this method to define the behavior to be executed after the class is being tested.
    * 
    * @throws Exception The exception by configuration
    */
   protected void onEnd() throws Exception {
      // Do nothing
   }
}
