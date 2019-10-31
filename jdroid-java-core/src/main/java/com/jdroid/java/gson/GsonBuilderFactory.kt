package com.jdroid.java.gson

import com.google.gson.GsonBuilder
import com.jdroid.java.date.DateConfiguration

object GsonBuilderFactory {

    fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat(DateConfiguration.getDefaultDateTimeFormat())
        gsonBuilder.disableHtmlEscaping()
        gsonBuilder.setExclusionStrategies(HiddenAnnotationExclusionStrategy())
        return gsonBuilder
    }
}
