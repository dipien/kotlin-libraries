package com.jdroid.java.gson

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class HiddenAnnotationExclusionStrategy : ExclusionStrategy {

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return clazz.getAnnotation<Hidden>(Hidden::class.java) != null
    }

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.getAnnotation(Hidden::class.java) != null
    }
}