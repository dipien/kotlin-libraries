package com.jdroid.java;

import com.jdroid.java.collections.Iterables;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Sets;
import com.jdroid.java.domain.Identifiable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

/**
 * Test class for {@link Assert} class.<br>
 * 
 */
public class AssertTest {
	
	private static final String DEFAULT_MSG = "Default Message";
	
	/**
	 * Data provider for the following methods:
	 * 
	 * <pre>
	 * {@link AssertTest#assertEqualsNoOrderTest(Iterable, Iterable)}
	 * {@link AssertTest#assertEqualsNoOrderMessageTest(Iterable, Iterable)}
	 * {@link AssertTest#assertEqualsNoOrderArraysTest(Iterable, Iterable)}
	 * {@link AssertTest#assertEqualsNoOrderArraysMessageTest(Iterable, Iterable)}
	 * </pre>
	 * 
	 * Provides successful cases.
	 * 
	 * @return {@link Object}[][] contains the parameters and their values.
	 */
	@DataProvider
	public Object[][] assertEqualsNoOrderDataProvider() {
		
		// Case 1: 2 ArrayLists with the same contents in the same order.
		Object[] case1 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 2, 3) };
		
		// Case 2: 2 HashSets with the same contents in the different order.
		Object[] case2 = { Sets.newHashSet(1, 1, 2, 3), Sets.newHashSet(3, 2, 1, 1) };
		
		// Case 3: 2 Empty ArrayLists.
		Object[] case3 = { Lists.newArrayList(), Lists.newArrayList() };
		
		// Case 4: 2 HashSets with same amount of only 1 content.
		Object[] case4 = { Sets.newHashSet(1, 1, 1, 1), Sets.newHashSet(1, 1, 1, 1) };
		
		// Case 5: The same ArrayList as both parameters.
		List<?> aux = Lists.newArrayList(1, 2, 3);
		Object[] case5 = { aux, aux };
		
		// Case 6: 1 ArrayList with the same contents of 1 HashSet.
		Object[] case6 = { Lists.newArrayList(1, 2, 3), Sets.newHashSet(1, 2, 3) };
		
		Object[][] result = { case1, case2, case3, case4, case5, case6 };
		return result;
	}
	
	/**
	 * Test method for the {@link Assert#assertEqualsNoOrder(Iterable, Iterable)} method.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderDataProvider()}.
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method.
	 */
	@Test(dataProvider = "assertEqualsNoOrderDataProvider")
	public void assertEqualsNoOrderTest(Iterable<Object> actual, Iterable<Object> expected) {
		Assert.assertEqualsNoOrder(actual, expected);
	}
	
	/**
	 * Test method for the {@link Assert#assertEqualsNoOrder(Iterable, Iterable, String)} method.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderDataProvider()}.
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method.
	 */
	@Test(dataProvider = "assertEqualsNoOrderDataProvider")
	public void assertEqualsNoOrderMessageTest(Iterable<Object> actual, Iterable<Object> expected) {
		Assert.assertEqualsNoOrder(actual, expected, AssertTest.DEFAULT_MSG);
	}
	
	/**
	 * Test method for the {@link Assert#assertEqualsNoOrder(Object[], Object[])} method.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderDataProvider()}.
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method. Needs to be converted
	 *            into an array.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method. Needs to be
	 *            converted into an array.
	 */
	@Test(dataProvider = "assertEqualsNoOrderDataProvider")
	public void assertEqualsNoOrderArraysTest(Iterable<Object> actual, Iterable<Object> expected) {
		Object[] actualArray = Iterables.toArray(actual, Object.class);
		Object[] expectedArray = Iterables.toArray(expected, Object.class);
		Assert.assertEqualsNoOrder(actualArray, expectedArray);
	}
	
	/**
	 * Test method for the {@link Assert#assertEqualsNoOrder(Object[], Object[], String)} method.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderDataProvider()}.
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method. Needs to be converted
	 *            into an array.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method. Needs to be
	 *            converted into an array.
	 */
	@Test(dataProvider = "assertEqualsNoOrderDataProvider")
	public void assertEqualsNoOrderArraysMessageTest(Iterable<Object> actual, Iterable<Object> expected) {
		Object[] actualArray = Iterables.toArray(actual, Object.class);
		Object[] expectedArray = Iterables.toArray(expected, Object.class);
		Assert.assertEqualsNoOrder(actualArray, expectedArray, AssertTest.DEFAULT_MSG);
	}
	
	/**
	 * Data provider for the following methods:
	 * 
	 * <pre>
	 * {@link AssertTest#assertEqualsNoOrderExceptionTest(Iterable, Iterable)}
	 * {@link AssertTest#assertEqualsNoOrderMessageExceptionTest(Iterable, Iterable)}
	 * {@link AssertTest#assertEqualsNoOrderArraysExceptionTest(Iterable, Iterable)}
	 * {@link AssertTest#assertEqualsNoOrderArraysMessageExceptionTest(Iterable, Iterable)}
	 * </pre>
	 * 
	 * Provides failed cases.
	 * 
	 * @return {@link Object}[][] contains the parameters and their values.
	 */
	@DataProvider
	public Object[][] assertEqualsNoOrderExceptionDataProvider() {
		
		// Case 1: 2 HashSets with the different contents.
		Object[] case1 = { Sets.newHashSet(1, 2, 3), Sets.newHashSet(4, 5, 6) };
		
		// Case 2: 2 ArrayLists with the repeated contents in the different
		// amounts.
		Object[] case2 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 1, 2, 3) };
		
		// Case 3: 2 ArrayLists with only one content with different repetitions
		// of it.
		Object[] case3 = { Lists.newArrayList(1, 1, 1), Lists.newArrayList(1, 1, 1, 1) };
		
		// Case 4: 1 ArrayList and 1 HashSet containing some contents in common.
		Object[] case4 = { Lists.newArrayList(1, 2, 3), Sets.newHashSet(2, 3, 4) };
		
		Object[][] result = { case1, case2, case3, case4 };
		return result;
	}
	
	/**
	 * Exceptions test method for the {@link Assert#assertEqualsNoOrder(Iterable, Iterable)} method.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderExceptionDataProvider()}.
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method.
	 */
	@Test(dataProvider = "assertEqualsNoOrderExceptionDataProvider", expectedExceptions = AssertionError.class)
	public void assertEqualsNoOrderExceptionTest(Iterable<Object> actual, Iterable<Object> expected) {
		Assert.assertEqualsNoOrder(actual, expected);
	}
	
	/**
	 * Exceptions test method for the {@link Assert#assertEqualsNoOrder(Iterable, Iterable, String)} method. The @expectedException
	 * annotation is not used because we need to check if the message is correct.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderExceptionDataProvider()}.
	 * 
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method.
	 */
	@Test(dataProvider = "assertEqualsNoOrderExceptionDataProvider")
	public void assertEqualsNoOrderMessageExceptionTest(Iterable<Object> actual, Iterable<Object> expected) {
		try {
			Assert.assertEqualsNoOrder(actual, expected, AssertTest.DEFAULT_MSG);
			org.testng.Assert.fail("AssertionError must have been thrown.");
		} catch (AssertionError error) {
			
			// Check if the message sent as parameter is within the AssertionError
			// message.
			boolean isContained = error.getMessage().contains(AssertTest.DEFAULT_MSG);
			org.testng.Assert.assertTrue(isContained);
		}
	}
	
	/**
	 * Exceptions test method for the {@link Assert#assertEqualsNoOrder(Object[], Object[])} method.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderExceptionDataProvider()}.
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method. Needs to be converted
	 *            into an array.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method. Needs to be
	 *            converted into an array.
	 */
	@Test(dataProvider = "assertEqualsNoOrderExceptionDataProvider", expectedExceptions = AssertionError.class)
	public void assertEqualsNoOrderArraysExceptionTest(Iterable<Object> actual, Iterable<Object> expected) {
		Object[] actualArray = Iterables.toArray(actual, Object.class);
		Object[] expectedArray = Iterables.toArray(expected, Object.class);
		Assert.assertEqualsNoOrder(actualArray, expectedArray);
	}
	
	/**
	 * Exceptions test method for the {@link Assert#assertEqualsNoOrder(Object[], Object[], String)} method.The @expectedException
	 * annotation is not used because we need to check if the message is correct.<br>
	 * Data provided by {@link AssertTest#assertEqualsNoOrderExceptionDataProvider()}.
	 * 
	 * @param actual {@link Iterable} instance containing the "actual" parameter for the method. Needs to be converted
	 *            into an array.
	 * @param expected {@link Iterable} instance containing the "expected" parameter for the method. Needs to be
	 *            converted into an array.
	 */
	@Test(dataProvider = "assertEqualsNoOrderExceptionDataProvider")
	public void assertEqualsNoOrderArraysMessageExceptionTest(Iterable<Object> actual, Iterable<Object> expected) {
		try {
			Object[] actualArray = Iterables.toArray(actual, Object.class);
			Object[] expectedArray = Iterables.toArray(expected, Object.class);
			Assert.assertEqualsNoOrder(actualArray, expectedArray, AssertTest.DEFAULT_MSG);
			org.testng.Assert.fail("AssertionError must have been thrown.");
		} catch (AssertionError error) {
			
			// Check if the message sent as parameter is within the AssertionError
			// message.
			boolean isContained = error.getMessage().contains(AssertTest.DEFAULT_MSG);
			org.testng.Assert.assertTrue(isContained);
		}
	}
	
	/**
	 * Data provider for the methods:
	 * 
	 * <pre>
	 * {@link AssertTest#assertContentsNotPresentArrayTest(Collection, List)}
	 * {@link AssertTest#assertContentsNotPresentIterableTest(Collection, List)}
	 * </pre>
	 * 
	 * @return {@link Object}[][] contains the parameters and their values.
	 */
	@DataProvider
	public Object[][] assertContentsNotPresentDataProvider() {
		
		// Case 1: Container does not contain a single object.
		Object[] case1 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList(4) };
		
		// Case 2: Container does not contain a series of objects.
		Object[] case2 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList(4, 5, 6) };
		
		// Case 3: Empty container does not contain a series of objects.
		Object[] case3 = { Lists.newArrayList(), Lists.newArrayList(1, 2, 3) };
		
		// Case 4: Container does not contain an empty list of objects.
		Object[] case4 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList() };
		
		Object[][] result = { case1, case2, case3, case4 };
		return result;
	}
	
	/**
	 * Test method for the {@link Assert#assertContentsNotPresent(Collection, Object...)} method.<br>
	 * Data provided by {@link AssertTest#assertContentsNotPresentDataProvider()} .
	 * 
	 * @param container The {@link Collection} that is tested.
	 * @param contents The series of contents to be tested if they are present in the collection.
	 */
	@Test(dataProvider = "assertContentsNotPresentDataProvider")
	public void assertContentsNotPresentArrayTest(Collection<Object> container, List<Object> contents) {
		Assert.assertContentsNotPresent(container, contents.toArray());
	}
	
	/**
	 * Test method for the {@link Assert#assertContentsNotPresent(Collection, Object...)} method.<br>
	 * Data provided by {@link AssertTest#assertContentsNotPresentDataProvider()} .
	 * 
	 * @param container The {@link Collection} that is tested.
	 * @param contents The series of contents to be tested if they are present in the collection.
	 */
	@Test(dataProvider = "assertContentsNotPresentDataProvider")
	public void assertContentsNotPresentIterableTest(Collection<Object> container, List<Object> contents) {
		Assert.assertContentsNotPresent(container, contents);
	}
	
	/**
	 * Data provider for the following methods:
	 * 
	 * <pre>
	 * {@link AssertTest#assertContentsNotPresentArrayExceptionTest(Collection, List)}
	 * {@link AssertTest#assertContentsNotPresentIterableExceptionTest(Collection, List)}
	 * </pre>
	 * 
	 * @return {@link Object}[][] contains the parameters and their values.
	 */
	@DataProvider
	public Object[][] assertContentsNotPresentExceptionDataProvider() {
		
		// Case 1: Container contains a single object.
		Object[] case1 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList(1) };
		
		// Case 2: Container contains some objects.
		Object[] case2 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 2, 4) };
		
		// Case 3: Container contains all objects.
		Object[] case3 = { Lists.newArrayList(1, 2, 3), Lists.newArrayList(1) };
		
		Object[][] result = { case1, case2, case3 };
		return result;
	}
	
	/**
	 * Test method for the {@link Assert#assertContentsNotPresent(Collection, Object...)} method.<br>
	 * Data provided by {@link AssertTest#assertContentsNotPresentDataProvider()} .
	 * 
	 * @param container The {@link Collection} that is tested.
	 * @param contents The series of contents to be tested if they are present in the collection.
	 */
	@Test(dataProvider = "assertContentsNotPresentExceptionDataProvider", expectedExceptions = AssertionError.class)
	public void assertContentsNotPresentArrayExceptionTest(Collection<Object> container, List<Object> contents) {
		Assert.assertContentsNotPresent(container, contents.toArray());
	}
	
	/**
	 * Test method for the {@link Assert#assertContentsNotPresent(Collection, Iterable)} method.<br>
	 * Data provided by {@link AssertTest#assertContentsNotPresentDataProvider()} .
	 * 
	 * @param container The {@link Collection} that is tested.
	 * @param contents The series of contents to be tested if they are present in the collection.
	 */
	@Test(dataProvider = "assertContentsNotPresentExceptionDataProvider", expectedExceptions = AssertionError.class)
	public void assertContentsNotPresentIterableExceptionTest(Collection<Object> container, List<Object> contents) {
		Assert.assertContentsNotPresent(container, contents);
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
	 * Data provider for the {@link AssertTest#assertEntityIdsExceptionDataProvider()}
	 * 
	 * @return {@link Object}[][] contains the parameters and their values.
	 */
	@DataProvider
	public Object[][] assertEntityIdsExceptionDataProvider() {
		List<Identifiable> actualEntities = Lists.newArrayList();
		List<String> expectedIds = Lists.newArrayList();
		
		// Case 1: Empty entity list. Single ID.
		expectedIds.add("1");
		Object[] case1 = { actualEntities, expectedIds };
		
		// Case 2: Single entity list. No IDs.
		actualEntities = Lists.newArrayList();
		expectedIds = Lists.newArrayList();
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "1";
			}
		});
		Object[] case2 = { actualEntities, expectedIds };
		
		// Case 2: Single entity list. Single ID. No match.
		actualEntities = Lists.newArrayList();
		expectedIds = Lists.newArrayList();
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "1";
			}
		});
		expectedIds.add("2");
		Object[] case3 = { actualEntities, expectedIds };
		
		// Case 4: Multiple entities list. Multiple IDs. No match.
		actualEntities = Lists.newArrayList();
		expectedIds = Lists.newArrayList();
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "1";
			}
		});
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "2";
			}
		});
		expectedIds.add("3");
		expectedIds.add("4");
		Object[] case4 = { actualEntities, expectedIds };
		
		// Case 5: Multiple entities list. Multiple IDs. Partial match.
		actualEntities = Lists.newArrayList();
		expectedIds = Lists.newArrayList();
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "1";
			}
		});
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "2";
			}
		});
		expectedIds.add("2");
		expectedIds.add("3");
		Object[] case5 = { actualEntities, expectedIds };
		
		// Case 6: Multiple repeated entities list. Single IDs. Match.
		actualEntities = Lists.newArrayList();
		expectedIds = Lists.newArrayList();
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "1";
			}
		});
		actualEntities.add(new Identifiable() {
			
			@Override
			public String getId() {
				return "1";
			}
		});
		expectedIds.add("1");
		Object[] case6 = { actualEntities, expectedIds };
		
		Object[][] result = { case1, case2, case3, case4, case5, case6 };
		return result;
	}
	
	// /**
	// * Test method for the {@link Assert#assertEntityIds(Collection, Collection)} method.<br>
	// *
	// * @param actualEntities The {@link List} of entities to assert.
	// * @param expectedIds The {@link List} of expected IDs.
	// * @param <T> Class implementing the {@link Identifiable} interface.
	// */
	// @Test(dataProvider = "assertEntityIdsExceptionDataProvider", expectedExceptions = AssertionError.class)
	// public <T extends Identifiable> void assertEntityIdsExceptionTest(List<T> actualEntities, List<Long> expectedIds)
	// {
	// Assert.assertEntityIds(actualEntities, expectedIds);
	// }
}
