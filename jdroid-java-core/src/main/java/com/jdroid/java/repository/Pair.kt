package com.jdroid.java.repository

import com.jdroid.java.domain.Entity

class Pair : Entity {

    var value: String? = null

    constructor(key: String, value: String) : super(key) {
        this.value = value
    }

    constructor() {}

    override fun toString(): String {
        val sb = StringBuffer("Pair{")
        sb.append("value='").append(value).append('\'')
        sb.append("} ")
        sb.append(super.toString())
        return sb.toString()
    }
}
