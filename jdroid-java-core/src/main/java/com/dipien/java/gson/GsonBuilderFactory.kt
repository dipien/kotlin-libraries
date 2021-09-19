package com.dipien.java.gson

import com.dipien.java.date.DateConfiguration
import com.google.gson.GsonBuilder

object GsonBuilderFactory {

    fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat(DateConfiguration.getDefaultDateTimeFormat())
        gsonBuilder.disableHtmlEscaping()
        gsonBuilder.setExclusionStrategies(HiddenAnnotationExclusionStrategy())
        return gsonBuilder
    }
}
