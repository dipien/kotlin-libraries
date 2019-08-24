package com.jdroid.java.utils

@Deprecated(message = "")
object ObjectUtils {

    /**
     *
     *
     * Compares two objects for equality, where either one or both objects may be `null`.
     *
     *
     *
     * <pre>
     * ObjectUtils.equals(null, null)                  = true
     * ObjectUtils.equals(null, "")                    = false
     * ObjectUtils.equals("", null)                    = false
     * ObjectUtils.equals("", "")                      = true
     * ObjectUtils.equals(Boolean.TRUE, null)          = false
     * ObjectUtils.equals(Boolean.TRUE, "true")        = false
     * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = true
     * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = false
    </pre> *
     *
     * @param object1 the first object, may be `null`
     * @param object2 the second object, may be `null`
     * @return `true` if the values of both objects are the same
     */
    fun equals(object1: Any?, object2: Any?): Boolean {
        if (object1 === object2) {
            return true
        }
        return if (object1 == null || object2 == null) {
            false
        } else object1 == object2
    }
}
