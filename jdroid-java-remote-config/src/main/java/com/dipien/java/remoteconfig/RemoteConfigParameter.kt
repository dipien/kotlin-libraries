package com.dipien.java.remoteconfig

interface RemoteConfigParameter {

    fun getKey(): String

    fun getDefaultValue(): Any?
}
