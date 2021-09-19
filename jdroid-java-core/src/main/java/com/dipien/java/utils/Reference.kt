package com.dipien.java.utils

import java.io.Serializable

class Reference<T> : Serializable {

    private var value: T? = null

    constructor() {}

    constructor(value: T) {
        this.value = value
    }

    fun get(): T? {
        return this.value
    }

    fun set(value: T) {
        this.value = value
    }
}
