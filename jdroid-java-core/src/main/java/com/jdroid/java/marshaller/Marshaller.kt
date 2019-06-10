package com.jdroid.java.marshaller

/**
 * @param <T>
 * @param <R>
 */
interface Marshaller<T, R> {

    fun marshall(value: T?, mode: MarshallerMode?, extras: Map<String, String>?): R?
}
