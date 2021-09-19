package com.dipien.java

import com.jdroid.java.collections.Sets
import org.junit.Test

/**
 * Test class for [Assert] class.<br></br>
 */
class AssertTest {

    companion object {
        private const val DEFAULT_MSG = "Default Message"
    }

    @Test
    fun assertEqualsNoOrderTest() {

        // Case 1: 2 ArrayLists with the same contents in the same order.
        assertEqualsNoOrderTest(listOf(1, 2, 3), listOf(1, 2, 3))

        // Case 2: 2 HashSets with the same contents in the different order.
        assertEqualsNoOrderTest(Sets.newHashSet(1, 1, 2, 3), Sets.newHashSet(3, 2, 1, 1))

        // Case 3: 2 Empty ArrayLists.
        assertEqualsNoOrderTest(listOf<Any>(), listOf<Any>())

        // Case 4: 2 HashSets with same amount of only 1 content.
        assertEqualsNoOrderTest(Sets.newHashSet(1, 1, 1, 1), Sets.newHashSet(1, 1, 1, 1))

        // Case 5: The same ArrayList as both parameters.
        val aux = listOf(1, 2, 3)
        assertEqualsNoOrderTest(aux, aux)

        // Case 6: 1 ArrayList with the same contents of 1 HashSet.
        assertEqualsNoOrderTest(listOf(1, 2, 3), Sets.newHashSet(1, 2, 3))
    }

    private fun assertEqualsNoOrderTest(actual: Iterable<Any>, expected: Iterable<Any>) {
        Assert.assertEqualsNoOrder(actual, expected)
        Assert.assertEqualsNoOrder(actual, expected, DEFAULT_MSG)
    }

    @Test(expected = AssertionError::class)
    fun assertEqualsNoOrderExceptionTest() {

        // Case 1: 2 HashSets with the different contents.
        assertEqualsNoOrderExceptionTest(Sets.newHashSet(1, 2, 3), Sets.newHashSet(4, 5, 6))

        // Case 2: 2 ArrayLists with the repeated contents in the different
        // amounts.
        assertEqualsNoOrderExceptionTest(listOf(1, 2, 3), listOf(1, 1, 2, 3))

        // Case 3: 2 ArrayLists with only one content with different repetitions
        // of it.
        assertEqualsNoOrderExceptionTest(listOf(1, 1, 1), listOf(1, 1, 1, 1))

        // Case 4: 1 ArrayList and 1 HashSet containing some contents in common.
        assertEqualsNoOrderExceptionTest(listOf(1, 2, 3), Sets.newHashSet(2, 3, 4))
    }

    private fun assertEqualsNoOrderExceptionTest(actual: Iterable<Any>, expected: Iterable<Any>) {
        Assert.assertEqualsNoOrder(actual, expected)
        try {
            Assert.assertEqualsNoOrder(actual, expected, DEFAULT_MSG)
            org.junit.Assert.fail("AssertionError must have been thrown.")
        } catch (error: AssertionError) {

            // Check if the message sent as parameter is within the AssertionError
            // message.
            val isContained = error.message!!.contains(DEFAULT_MSG)
            org.junit.Assert.assertTrue(isContained)
        }
    }

    @Test
    fun assertContentsNotPresentArrayTest() {

        // Case 1: Container does not contain a single object.
        assertContentsNotPresentArrayTest(listOf(1, 2, 3), listOf(4))

        // Case 2: Container does not contain a series of objects.
        assertContentsNotPresentArrayTest(listOf(1, 2, 3), listOf(4, 5, 6))

        // Case 3: Empty container does not contain a series of objects.
        assertContentsNotPresentArrayTest(listOf<Any>(), listOf(1, 2, 3))

        // Case 4: Container does not contain an empty list of objects.
        assertContentsNotPresentArrayTest(listOf(1, 2, 3), listOf<Any>())
    }

    private fun assertContentsNotPresentArrayTest(container: Collection<Any>, contents: List<Any>) {
        Assert.assertContentsNotPresent(container, *contents.toTypedArray())
        Assert.assertContentsNotPresent(container, contents)
    }

    @Test(expected = AssertionError::class)
    fun assertContentsNotPresentArrayExceptionTest() {

        // Case 1: Container contains a single object.
        assertContentsNotPresentArrayExceptionTest(listOf(1, 2, 3), listOf(1))

        // Case 2: Container contains some objects.
        assertContentsNotPresentArrayExceptionTest(listOf(1, 2, 3), listOf(1, 2, 4))

        // Case 3: Container contains all objects.
        assertContentsNotPresentArrayExceptionTest(listOf(1, 2, 3), listOf(1))
    }

    private fun assertContentsNotPresentArrayExceptionTest(container: Collection<Any>, contents: List<Any>) {
        Assert.assertContentsNotPresent(container, *contents.toTypedArray())
        Assert.assertContentsNotPresent(container, contents)
    }

    // /**
    // * Data provider for the {@link AssertTest#assertEntityIdsTest(List, List)} method.
    // *
    // * @return {@link Object}[][] contains the parameters and their values.
    // */
    // @DataProvider
    // public Object[][] assertEntityIdsDataProvider() {
    // List<Identifiable> actualEntities = mutableListOf();
    // List<Long> expectedIds = mutableListOf();
    //
    // // Case 1: Empty lists.
    // Object[] case1 = { actualEntities, expectedIds };
    //
    // // Case 2: Single entity list. Single ID. Match.
    // actualEntities = mutableListOf();
    // expectedIds = mutableListOf();
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
    // actualEntities = mutableListOf();
    // expectedIds = mutableListOf();
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
    // actualEntities = mutableListOf();
    // expectedIds = mutableListOf();
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
    // actualEntities = mutableListOf();
    // expectedIds = mutableListOf();
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

    // /**
    //  * Data provider for the [AssertTest.assertEntityIdsExceptionDataProvider]
    //  *
    //  * @return [Object][][] contains the parameters and their values.
    //  */
    // @DataProvider
    // fun assertEntityIdsExceptionDataProvider(): Array<Array<Any>> {
    //     var actualEntities: MutableList<Identifiable> = mutableListOf()
    //     var expectedIds: MutableList<String> = mutableListOf()
    //
    //     // Case 1: Empty entity list. Single ID.
    //     expectedIds.add("1")
    //     val case1 = arrayOf<Any>(actualEntities, expectedIds)
    //
    //     // Case 2: Single entity list. No IDs.
    //     actualEntities = mutableListOf()
    //     expectedIds = mutableListOf()
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "1"
    //         }
    //     })
    //     val case2 = arrayOf<Any>(actualEntities, expectedIds)
    //
    //     // Case 2: Single entity list. Single ID. No match.
    //     actualEntities = mutableListOf()
    //     expectedIds = mutableListOf()
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "1"
    //         }
    //     })
    //     expectedIds.add("2")
    //     val case3 = arrayOf<Any>(actualEntities, expectedIds)
    //
    //     // Case 4: Multiple entities list. Multiple IDs. No match.
    //     actualEntities = mutableListOf()
    //     expectedIds = mutableListOf()
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "1"
    //         }
    //     })
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "2"
    //         }
    //     })
    //     expectedIds.add("3")
    //     expectedIds.add("4")
    //     val case4 = arrayOf<Any>(actualEntities, expectedIds)
    //
    //     // Case 5: Multiple entities list. Multiple IDs. Partial match.
    //     actualEntities = mutableListOf()
    //     expectedIds = mutableListOf()
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "1"
    //         }
    //     })
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "2"
    //         }
    //     })
    //     expectedIds.add("2")
    //     expectedIds.add("3")
    //     val case5 = arrayOf<Any>(actualEntities, expectedIds)
    //
    //     // Case 6: Multiple repeated entities list. Single IDs. Match.
    //     actualEntities = mutableListOf()
    //     expectedIds = mutableListOf()
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "1"
    //         }
    //     })
    //     actualEntities.add(object : Identifiable {
    //
    //         override fun getId(): String? {
    //             return "1"
    //         }
    //     })
    //     expectedIds.add("1")
    //     val case6 = arrayOf<Any>(actualEntities, expectedIds)
    //
    //     return arrayOf(case1, case2, case3, case4, case5, case6)
    // }
}
