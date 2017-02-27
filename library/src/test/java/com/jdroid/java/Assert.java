package com.jdroid.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.jdroid.java.collections.Iterables;
import com.jdroid.java.collections.Lists;

/**
 * Assertion tool class. Presents assertion methods with a more natural parameter order. The order is always
 * actualValue, expectedValue [, message].
 * 
 * @see org.testng.Assert
 */
public class Assert extends org.testng.Assert {
	
	/**
	 * Asserts that two {@link Iterable} instances contain the same elements in no particular order. If they do not, an
	 * AssertionError is thrown.<br>
	 * This method replaces the use of {@link org.testng.Assert#assertEqualsNoOrder(Object[], Object[])}.
	 * 
	 * @param actual The actual value.
	 * @param expected The expected value.
	 */
	public static void assertEqualsNoOrder(Iterable<?> actual, Iterable<?> expected) {
		Assert.assertEqualsNoOrder(actual, expected, null);
	}
	
	/**
	 * Asserts that two {@link Iterable} instances contain the same elements in no particular order. If they do not, an
	 * AssertionError, with the given message, is thrown.<br>
	 * This method replaces the use of {@link org.testng.Assert#assertEqualsNoOrder(Object[], Object[], String)}.
	 * 
	 * @param actual The actual value.
	 * @param expected The expected value.
	 * @param message The assertion error message.
	 */
	public static void assertEqualsNoOrder(Iterable<?> actual, Iterable<?> expected, String message) {
		
		// Check if both iterables have the same size.
		org.testng.Assert.assertEquals(Iterables.size(actual), Iterables.size(expected), message);
		
		// Create a list based on the expected results.
		List<?> expectedList = Lists.newArrayList(expected);
		
		// Iterate over the obtained results and check if the expected list
		// contains the item. If the item is contained within the expected list
		// then it is removed from it so that repeated items can be checked.
		for (Object o : actual) {
			org.testng.Assert.assertTrue(expectedList.contains(o), message);
			expectedList.remove(o);
		}
	}
	
	/**
	 * Asserts that two arrays contain the same elements in no particular order. If they do not, an AssertionError is
	 * thrown.<br>
	 * This method "overrides" the {@link org.testng.Assert#assertEqualsNoOrder(Object[], Object[])} method. It converts
	 * the parameters to {@link ArrayList} instances and passes them as parameters to the
	 * {@link Assert#assertEqualsNoOrder(Iterable, Iterable)} method.
	 * 
	 * @param actual The actual value.
	 * @param expected The expected value.
	 */
	public static void assertEqualsNoOrder(Object[] actual, Object[] expected) {
		Assert.assertEqualsNoOrder(Lists.newArrayList(actual), Lists.newArrayList(expected));
	}
	
	/**
	 * Asserts that two arrays contain the same elements in no particular order. If they do not, an AssertionError, with
	 * the given message, is thrown.<br>
	 * This method "overrides" the {@link org.testng.Assert#assertEqualsNoOrder(Object[], Object[], String)} method. It
	 * converts the actual and expectedparamaters to {@link ArrayList} instances and passes them as parameters to the
	 * {@link Assert#assertEqualsNoOrder(Iterable, Iterable, String)} method.
	 * 
	 * @param actual The actual value.
	 * @param expected The expected value.
	 * @param message The assertion error message.
	 */
	public static void assertEqualsNoOrder(Object[] actual, Object[] expected, String message) {
		Assert.assertEqualsNoOrder(Lists.newArrayList(actual), Lists.newArrayList(expected), message);
	}
	
	/**
	 * Asserts that a series of {@link Object} instances are not within a determined {@link Collection}.
	 * 
	 * @param <T> The generic type of the contents and the collection.
	 * @param container The {@link Collection} that is tested.
	 * @param contents The series of contents to be tested if they are present in the collection.
	 */
	public static <T> void assertContentsNotPresent(Collection<T> container, Iterable<T> contents) {
		for (T content : contents) {
			org.testng.Assert.assertFalse(container.contains(content));
		}
	}
	
	/**
	 * Asserts that a series of {@link Object} instances are not within a determined {@link Collection}.<br>
	 * Calls the {@link Assert#assertContentsNotPresent(Collection, Iterable)} method passing the contents as a
	 * {@link List}.
	 * 
	 * @see Assert#assertContentsNotPresent(Collection, Iterable)
	 * @param <T> The generic type of the contents and the collection.
	 * @param container The {@link Collection} that is tested.
	 * @param contents The series of contents to be tested if they are present in the collection.
	 */
	@SafeVarargs
	public static <T> void assertContentsNotPresent(Collection<T> container, T... contents) {
		Assert.assertContentsNotPresent(container, Lists.newArrayList(contents));
	}
	
	// /**
	// * Asserts that a list of Identifiables are the ones with the expected IDs.<br>
	// * Throws an {@link AssertionError} if they don't match.
	// *
	// * @param <T> Class that extends {@link Identifiable}
	// * @param actualIdentifiables The {@link List} of Identifiables to assert.
	// * @param expectedIds The {@link List} of expected IDs.
	// */
	// public static <T extends Identifiable> void assertEntityIds(Collection<T> actualIdentifiables,
	// Collection<Long> expectedIds) {
	// Collection<Long> actualIds = Collections2.transform(actualIdentifiables, new IdPropertyFunction());
	// org.testng.Assert.assertEquals(actualIds.size(), expectedIds.size());
	// org.testng.Assert.assertEquals(Sets.newHashSet(actualIds), Sets.newHashSet(expectedIds));
	// }
}
