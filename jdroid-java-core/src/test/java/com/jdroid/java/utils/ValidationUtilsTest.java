package com.jdroid.java.utils;

import java.util.Iterator;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.utils.ValidationUtils;

/**
 * Test for ValidationUtils
 * 
 */
public class ValidationUtilsTest {
	
	/**
	 * @return The different scenarios for test {@link ValidationUtils#isValidURL(String)}
	 */
	@DataProvider
	public Iterator<Object[]> urlDataProvider() {
		List<Object[]> cases = Lists.newArrayList();
		
		cases.add(new Object[] { "http://www.validUrl.com", true });
		cases.add(new Object[] { "http://www.validUrl.com/;kw=[service,110343]", true });
		cases.add(new Object[] { "invalidUrl", false });
		cases.add(new Object[] { "htp://invalid.com", false });
		
		return cases.iterator();
	}
	
	/**
	 * @param value The Url value as String to Validate
	 * @param expectedValue the expected result
	 */
	@Test(dataProvider = "urlDataProvider")
	public void isValidURL(String value, boolean expectedValue) {
		Assert.assertEquals(ValidationUtils.isValidURL(value), expectedValue);
	}
}
