package com.jdroid.java.annotation

/**
 * Annotation to tag all the internal APIs that shouldn't be used by the library users
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class Internal
