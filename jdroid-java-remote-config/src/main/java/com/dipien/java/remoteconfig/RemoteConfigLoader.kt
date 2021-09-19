package com.dipien.java.remoteconfig

interface RemoteConfigLoader {

    fun fetch()

    fun getObject(remoteConfigParameter: RemoteConfigParameter): Any?

    fun getString(remoteConfigParameter: RemoteConfigParameter): String?

    fun getStringList(remoteConfigParameter: RemoteConfigParameter): List<String>?

    fun getBoolean(remoteConfigParameter: RemoteConfigParameter): Boolean?

    fun getLong(remoteConfigParameter: RemoteConfigParameter): Long?

    fun getDouble(remoteConfigParameter: RemoteConfigParameter): Double?
}
