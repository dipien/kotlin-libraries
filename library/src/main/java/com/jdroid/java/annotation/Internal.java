package com.jdroid.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to tag all the internal APIs that shouldn't be used by the library users
 */
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Internal {
}
