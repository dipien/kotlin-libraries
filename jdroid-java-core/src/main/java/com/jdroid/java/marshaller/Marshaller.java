package com.jdroid.java.marshaller;

import java.util.Map;

/**
 * @param <T>
 * @param <R>
 */
public interface Marshaller<T, R> {
	
	public R marshall(T object, MarshallerMode mode, Map<String, String> extras);
}
