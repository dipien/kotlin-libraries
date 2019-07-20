package com.jdroid.java;

import org.mockito.ArgumentMatcher;

import java.util.Collection;

/**
 * Matcher that verify the argument is contained by the collection
 *
 * @param <E> The collection type
 */
public class CollectionMatcher<E> extends ArgumentMatcher<E> {

	private Collection<E> collection;

	/**
	 * @param collection The collection
	 */
	public CollectionMatcher(Collection<E> collection) {
		this.collection = collection;
	}

	@Override
	public boolean matches(Object argument) {
		return this.collection.contains(argument);
	}

}
