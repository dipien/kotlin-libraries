package com.dipien.core

import com.dipien.core.collections.Iterables
import com.dipien.core.collections.Lists

/**
 * Assertion tool class. Presents assertion methods with a more natural parameter order. The order is always
 * actualValue, expectedValue [, message].
 *
 * @see org.testng.Assert
 */
object Assert : org.junit.Assert() {

    /**
     * Asserts that two [Iterable] instances contain the same elements in no particular order. If they do not, an
     * AssertionError is thrown.<br></br>
     * This method replaces the use of [org.junit.Assert.assertEqualsNoOrder].
     *
     * @param actual The actual value.
     * @param expected The expected value.
     */
    fun assertEqualsNoOrder(actual: Iterable<*>, expected: Iterable<*>) {
        assertEqualsNoOrder(actual, expected, null)
    }

    /**
     * Asserts that two [Iterable] instances contain the same elements in no particular order. If they do not, an
     * AssertionError, with the given message, is thrown.<br></br>
     * This method replaces the use of [org.junit.Assert.assertEqualsNoOrder].
     *
     * @param actual The actual value.
     * @param expected The expected value.
     * @param message The assertion error message.
     */
    fun assertEqualsNoOrder(actual: Iterable<*>, expected: Iterable<*>, message: String?) {

        // Check if both iterables have the same size.
        assertEquals(message, Iterables.size(expected), Iterables.size(actual))

        // Create a list based on the expected results.
        val expectedList = Lists.newArrayList(expected)

        // Iterate over the obtained results and check if the expected list
        // contains the item. If the item is contained within the expected list
        // then it is removed from it so that repeated items can be checked.
        for (o in actual) {
            assertTrue(message, expectedList.contains(o))
            expectedList.remove(o)
        }
    }

    /**
     * Asserts that a series of [Object] instances are not within a determined [Collection].
     *
     * @param <T> The generic type of the contents and the collection.
     * @param container The [Collection] that is tested.
     * @param contents The series of contents to be tested if they are present in the collection.
    </T> */
    fun <T> assertContentsNotPresent(container: Collection<T>, contents: Iterable<T>) {
        for (content in contents) {
            assertFalse(container.contains(content))
        }
    }

    /**
     * Asserts that a series of [Object] instances are not within a determined [Collection].<br></br>
     * Calls the [Assert.assertContentsNotPresent] method passing the contents as a
     * [List].
     *
     * @param <T> The generic type of the contents and the collection.
     * @param container The [Collection] that is tested.
     * @param contents The series of contents to be tested if they are present in the collection.
     * @see Assert.assertContentsNotPresent
    </T> */
    @SafeVarargs
    fun <T> assertContentsNotPresent(container: Collection<T>, vararg contents: T) {
        assertContentsNotPresent(container, listOf(*contents))
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
