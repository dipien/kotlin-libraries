package com.dipien.core.gson

import com.dipien.core.date.DateConfiguration
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
