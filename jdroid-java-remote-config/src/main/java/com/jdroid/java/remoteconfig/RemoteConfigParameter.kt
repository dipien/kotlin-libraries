package com.jdroid.java.remoteconfig

interface RemoteConfigParameter {

    fun getKey(): String

    fun getDefaultValue(): Any?
}
