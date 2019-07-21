package com.jdroid.java

import com.jdroid.java.collections.Lists
import com.jdroid.java.collections.Sets
import com.jdroid.java.domain.Identifiable

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

/**
 * Test class for [Assert] class.<br></br>
 */
class AssertTest {

    companion object {
        private const val DEFAULT_MSG = "Default Message"
    }

    /**
     * Data provider for the following methods:
     *
     *
     * <pre>
     * [AssertTest.assertEqualsNoOrderTest]
     * [AssertTest.assertEqualsNoOrderMessageTest]
    </pre> *
     *
     *
     * Provides successful cases.
     *
     * @return [Object][][] contains the parameters and their values.
     */
    @DataProvider
    fun assertEqualsNoOrderDataProvider(): Array<Array<Any>> {

        // Case 1: 2 ArrayLists with the same contents in the same order.
        val case1 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 2, 3))

        // Case 2: 2 HashSets with the same contents in the different order.
        val case2 = arrayOf<Any>(Sets.newHashSet(1, 1, 2, 3), Sets.newHashSet(3, 2, 1, 1))

        // Case 3: 2 Empty ArrayLists.
        val case3 = arrayOf<Any>(Lists.newArrayList<Any>(), Lists.newArrayList<Any>())

        // Case 4: 2 HashSets with same amount of only 1 content.
        val case4 = arrayOf<Any>(Sets.newHashSet(1, 1, 1, 1), Sets.newHashSet(1, 1, 1, 1))

        // Case 5: The same ArrayList as both parameters.
        val aux = Lists.newArrayList(1, 2, 3)
        val case5 = arrayOf<Any>(aux, aux)

        // Case 6: 1 ArrayList with the same contents of 1 HashSet.
        val case6 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Sets.newHashSet(1, 2, 3))

        return arrayOf(case1, case2, case3, case4, case5, case6)
    }

    /**
     * Test method for the [Assert.assertEqualsNoOrder] method.<br></br>
     * Data provided by [AssertTest.assertEqualsNoOrderDataProvider].
     *
     * @param actual [Iterable] instance containing the "actual" parameter for the method.
     * @param expected [Iterable] instance containing the "expected" parameter for the method.
     */
    @Test(dataProvider = "assertEqualsNoOrderDataProvider")
    fun assertEqualsNoOrderTest(actual: Iterable<Any>, expected: Iterable<Any>) {
        Assert.assertEqualsNoOrder(actual, expected)
    }

    /**
     * Test method for the [Assert.assertEqualsNoOrder] method.<br></br>
     * Data provided by [AssertTest.assertEqualsNoOrderDataProvider].
     *
     * @param actual [Iterable] instance containing the "actual" parameter for the method.
     * @param expected [Iterable] instance containing the "expected" parameter for the method.
     */
    @Test(dataProvider = "assertEqualsNoOrderDataProvider")
    fun assertEqualsNoOrderMessageTest(actual: Iterable<Any>, expected: Iterable<Any>) {
        Assert.assertEqualsNoOrder(actual, expected, DEFAULT_MSG)
    }

    /**
     * Data provider for the following methods:
     *
     *
     * <pre>
     * [AssertTest.assertEqualsNoOrderExceptionTest]
     * [AssertTest.assertEqualsNoOrderMessageExceptionTest]
    </pre> *
     *
     *
     * Provides failed cases.
     *
     * @return [Object][][] contains the parameters and their values.
     */
    @DataProvider
    fun assertEqualsNoOrderExceptionDataProvider(): Array<Array<Any>> {

        // Case 1: 2 HashSets with the different contents.
        val case1 = arrayOf<Any>(Sets.newHashSet(1, 2, 3), Sets.newHashSet(4, 5, 6))

        // Case 2: 2 ArrayLists with the repeated contents in the different
        // amounts.
        val case2 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 1, 2, 3))

        // Case 3: 2 ArrayLists with only one content with different repetitions
        // of it.
        val case3 = arrayOf<Any>(Lists.newArrayList(1, 1, 1), Lists.newArrayList(1, 1, 1, 1))

        // Case 4: 1 ArrayList and 1 HashSet containing some contents in common.
        val case4 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Sets.newHashSet(2, 3, 4))

        return arrayOf(case1, case2, case3, case4)
    }

    /**
     * Exceptions test method for the [Assert.assertEqualsNoOrder] method.<br></br>
     * Data provided by [AssertTest.assertEqualsNoOrderExceptionDataProvider].
     *
     * @param actual [Iterable] instance containing the "actual" parameter for the method.
     * @param expected [Iterable] instance containing the "expected" parameter for the method.
     */
    @Test(dataProvider = "assertEqualsNoOrderExceptionDataProvider", expectedExceptions = [AssertionError::class])
    fun assertEqualsNoOrderExceptionTest(actual: Iterable<Any>, expected: Iterable<Any>) {
        Assert.assertEqualsNoOrder(actual, expected)
    }

    /**
     * Exceptions test method for the [Assert.assertEqualsNoOrder] method. The @expectedException
     * annotation is not used because we need to check if the message is correct.<br></br>
     * Data provided by [AssertTest.assertEqualsNoOrderExceptionDataProvider].
     *
     * @param actual [Iterable] instance containing the "actual" parameter for the method.
     * @param expected [Iterable] instance containing the "expected" parameter for the method.
     */
    @Test(dataProvider = "assertEqualsNoOrderExceptionDataProvider")
    fun assertEqualsNoOrderMessageExceptionTest(actual: Iterable<Any>, expected: Iterable<Any>) {
        try {
            Assert.assertEqualsNoOrder(actual, expected, DEFAULT_MSG)
            org.testng.Assert.fail("AssertionError must have been thrown.")
        } catch (error: AssertionError) {

            // Check if the message sent as parameter is within the AssertionError
            // message.
            val isContained = error.message!!.contains(DEFAULT_MSG)
            org.testng.Assert.assertTrue(isContained)
        }
    }

    /**
     * Data provider for the methods:
     *
     *
     * <pre>
     * [AssertTest.assertContentsNotPresentArrayTest]
     * [AssertTest.assertContentsNotPresentIterableTest]
    </pre> *
     *
     * @return [Object][][] contains the parameters and their values.
     */
    @DataProvider
    fun assertContentsNotPresentDataProvider(): Array<Array<Any>> {

        // Case 1: Container does not contain a single object.
        val case1 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList(4))

        // Case 2: Container does not contain a series of objects.
        val case2 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList(4, 5, 6))

        // Case 3: Empty container does not contain a series of objects.
        val case3 = arrayOf<Any>(Lists.newArrayList<Any>(), Lists.newArrayList(1, 2, 3))

        // Case 4: Container does not contain an empty list of objects.
        val case4 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList<Any>())

        return arrayOf(case1, case2, case3, case4)
    }

    /**
     * Test method for the [Assert.assertContentsNotPresent] method.<br></br>
     * Data provided by [AssertTest.assertContentsNotPresentDataProvider] .
     *
     * @param container The [Collection] that is tested.
     * @param contents The series of contents to be tested if they are present in the collection.
     */
    @Test(dataProvider = "assertContentsNotPresentDataProvider")
    fun assertContentsNotPresentArrayTest(container: Collection<Any>, contents: List<Any>) {
        Assert.assertContentsNotPresent(container, *contents.toTypedArray())
    }

    /**
     * Test method for the [Assert.assertContentsNotPresent] method.<br></br>
     * Data provided by [AssertTest.assertContentsNotPresentDataProvider] .
     *
     * @param container The [Collection] that is tested.
     * @param contents The series of contents to be tested if they are present in the collection.
     */
    @Test(dataProvider = "assertContentsNotPresentDataProvider")
    fun assertContentsNotPresentIterableTest(container: Collection<Any>, contents: List<Any>) {
        Assert.assertContentsNotPresent(container, contents)
    }

    /**
     * Data provider for the following methods:
     *
     *
     * <pre>
     * [AssertTest.assertContentsNotPresentArrayExceptionTest]
     * [AssertTest.assertContentsNotPresentIterableExceptionTest]
    </pre> *
     *
     * @return [Object][][] contains the parameters and their values.
     */
    @DataProvider
    fun assertContentsNotPresentExceptionDataProvider(): Array<Array<Any>> {

        // Case 1: Container contains a single object.
        val case1 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList(1))

        // Case 2: Container contains some objects.
        val case2 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 2, 4))

        // Case 3: Container contains all objects.
        val case3 = arrayOf<Any>(Lists.newArrayList(1, 2, 3), Lists.newArrayList(1))

        return arrayOf(case1, case2, case3)
    }

    /**
     * Test method for the [Assert.assertContentsNotPresent] method.<br></br>
     * Data provided by [AssertTest.assertContentsNotPresentDataProvider] .
     *
     * @param container The [Collection] that is tested.
     * @param contents The series of contents to be tested if they are present in the collection.
     */
    @Test(dataProvider = "assertContentsNotPresentExceptionDataProvider", expectedExceptions = [AssertionError::class])
    fun assertContentsNotPresentArrayExceptionTest(container: Collection<Any>, contents: List<Any>) {
        Assert.assertContentsNotPresent(container, *contents.toTypedArray())
    }

    /**
     * Test method for the [Assert.assertContentsNotPresent] method.<br></br>
     * Data provided by [AssertTest.assertContentsNotPresentDataProvider] .
     *
     * @param container The [Collection] that is tested.
     * @param contents The series of contents to be tested if they are present in the collection.
     */
    @Test(dataProvider = "assertContentsNotPresentExceptionDataProvider", expectedExceptions = [AssertionError::class])
    fun assertContentsNotPresentIterableExceptionTest(container: Collection<Any>, contents: List<Any>) {
        Assert.assertContentsNotPresent(container, contents)
    }

    // /**
    // * Data provider for the {@link AssertTest#assertEntityIdsTest(List, List)} method.
    // *
    // * @return {@link Object}[][] contains the parameters and their values.
    // */
    // @DataProvider
    // public Object[][] assertEntityIdsDataProvider() {
    // List<Identifiable> actualEntities = Lists.newArrayList();
    // List<Long> expectedIds = Lists.newArrayList();
    //
    // // Case 1: Empty lists.
    // Object[] case1 = { actualEntities, expectedIds };
    //
    // // Case 2: Single entity list. Single ID. Match.
    // actualEntities = Lists.newArrayList();
    // expectedIds = Lists.newArrayList();
    // actualEntities.add(new Identifiable() {
    //
    // @Override
    // public Long getId() {
    // return 1l;
    // }
    // });
    // expectedIds.add(1L);
    // Object[] case2 = { actualEntities, expectedIds };
    //
    // // Case 3: Multiple entities list. Multiple IDs. Match.
    // actualEntities = Lists.newArrayList();
    // expectedIds = Lists.newArrayList();
    // actualEntities.add(new Identifiable() {
    //
    // @Override
    // public Long getId() {
    // return 1l;
    // }
    // });
    // actualEntities.add(new Identifiable() {
    //
    // @Override
    // public Long getId() {
    // return 2l;
    // }
    // });
    // expectedIds.add(1L);
    // expectedIds.add(2L);
    // Object[] case3 = { actualEntities, expectedIds };
    //
    // // Case 4: Multiple repeated entities list. Multiple repeated IDs. No
    // // Match.
    // actualEntities = Lists.newArrayList();
    // expectedIds = Lists.newArrayList();
    // actualEntities.add(new Identifiable() {
    //
    // @Override
    // public Long getId() {
    // return 1l;
    // }
    // });
    // actualEntities.add(new Identifiable() {
    //
    // @Override
    // public Long getId() {
    // return 1l;
    // }
    // });
    // expectedIds.add(1L);
    // expectedIds.add(1L);
    // Object[] case4 = { actualEntities, expectedIds };
    //
    // // Case 5: Multiple repeated entities list. Single IDs. Unsorted match.
    // actualEntities = Lists.newArrayList();
    // expectedIds = Lists.newArrayList();
    // actualEntities.add(new Identifiable() {
    //
    // @Override
    // public Long getId() {
    // return 1l;
    // }
    // });
    // actualEntities.add(new Identifiable() {
    //
    // @Override
    // public Long getId() {
    // return 2l;
    // }
    // });
    // expectedIds.add(2L);
    // expectedIds.add(1L);
    // Object[] case5 = { actualEntities, expectedIds };
    //
    // Object[][] result = { case1, case2, case3, case4, case5 };
    // return result;
    // }

    // /**
    // * Test method for the {@link Assert#assertEntityIds(Collection, Collection)} method.<br>
    // * Data provided by the {@link AssertTest#assertEntityIdsDataProvider()} method.
    // *
    // * @param actualEntities The {@link List} of entities to assert.
    // * @param expectedIds The {@link List} of expected IDs.
    // * @param <T> Class implementing the {@link Identifiable} interface.
    // */
    // @Test(dataProvider = "assertEntityIdsDataProvider")
    // public <T extends Identifiable> void assertEntityIdsTest(List<T> actualEntities, List<Long> expectedIds) {
    // Assert.assertEntityIds(actualEntities, expectedIds);
    // }

    /**
     * Data provider for the [AssertTest.assertEntityIdsExceptionDataProvider]
     *
     * @return [Object][][] contains the parameters and their values.
     */
    @DataProvider
    fun assertEntityIdsExceptionDataProvider(): Array<Array<Any>> {
        var actualEntities: MutableList<Identifiable> = Lists.newArrayList()
        var expectedIds: MutableList<String> = Lists.newArrayList()

        // Case 1: Empty entity list. Single ID.
        expectedIds.add("1")
        val case1 = arrayOf<Any>(actualEntities, expectedIds)

        // Case 2: Single entity list. No IDs.
        actualEntities = Lists.newArrayList()
        expectedIds = Lists.newArrayList()
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "1"
            }
        })
        val case2 = arrayOf<Any>(actualEntities, expectedIds)

        // Case 2: Single entity list. Single ID. No match.
        actualEntities = Lists.newArrayList()
        expectedIds = Lists.newArrayList()
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "1"
            }
        })
        expectedIds.add("2")
        val case3 = arrayOf<Any>(actualEntities, expectedIds)

        // Case 4: Multiple entities list. Multiple IDs. No match.
        actualEntities = Lists.newArrayList()
        expectedIds = Lists.newArrayList()
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "1"
            }
        })
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "2"
            }
        })
        expectedIds.add("3")
        expectedIds.add("4")
        val case4 = arrayOf<Any>(actualEntities, expectedIds)

        // Case 5: Multiple entities list. Multiple IDs. Partial match.
        actualEntities = Lists.newArrayList()
        expectedIds = Lists.newArrayList()
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "1"
            }
        })
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "2"
            }
        })
        expectedIds.add("2")
        expectedIds.add("3")
        val case5 = arrayOf<Any>(actualEntities, expectedIds)

        // Case 6: Multiple repeated entities list. Single IDs. Match.
        actualEntities = Lists.newArrayList()
        expectedIds = Lists.newArrayList()
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "1"
            }
        })
        actualEntities.add(object : Identifiable {

            override fun getId(): String {
                return "1"
            }
        })
        expectedIds.add("1")
        val case6 = arrayOf<Any>(actualEntities, expectedIds)

        return arrayOf(case1, case2, case3, case4, case5, case6)
    }
}
