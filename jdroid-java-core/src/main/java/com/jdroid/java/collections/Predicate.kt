package com.jdroid.java.collections

/**
 * Determines a true or false value for a given input.
 *
 * @param <T>
 */
interface Predicate<T> {

    /**
	 * Returns the result of applying this predicate to `input`. This method is *generally expected*, but not
	 * absolutely required, to have the following properties:
	 *
	 *  * Its execution does not cause any observable side effects.
	 *
	 * @param input The input to evaluate
	 * @return True or false
	 */
    fun apply(input: T): Boolean
}
