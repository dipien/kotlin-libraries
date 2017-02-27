package com.jdroid.java.collections;

import java.util.Iterator;

public class Iterators {
	
	/**
	 * Returns the number of elements remaining in {@code iterator}. The iterator will be left exhausted: its
	 * {@code hasNext()} method will return {@code false}.
	 * 
	 * @param iterator The {@link Iterator}
	 * @return The {@link Iterator} size
	 */
	public static int size(Iterator<?> iterator) {
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}
}
