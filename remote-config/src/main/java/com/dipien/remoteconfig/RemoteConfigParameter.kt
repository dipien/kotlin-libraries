package com.dipien.remoteconfig

interface RemoteConfigParameter {

    fun getKey(): String

    fun getDefaultValue(): Any?
}
