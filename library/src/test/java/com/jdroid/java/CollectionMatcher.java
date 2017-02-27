package com.jdroid.java;

import java.util.Collection;
import org.mockito.ArgumentMatcher;

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
	
	/**
	 * @see org.mockito.ArgumentMatcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object argument) {
		return this.collection.contains(argument);
	}
	
}
