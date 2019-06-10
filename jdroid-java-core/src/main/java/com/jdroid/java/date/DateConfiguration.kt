package com.jdroid.java.date

import java.util.Date

object DateConfiguration {

    private var defaultDateTimeFormat = DateTimeFormat.YYYYMMDDHHMMSSZ
    private var fakeNow: Date? = null

    fun getFakeNow(): Date? {
        return fakeNow
    }

    fun setFakeNow(fakeNow: Date?) {
        DateConfiguration.fakeNow = fakeNow
    }

    fun isFakeNow(): Boolean {
        return fakeNow != null
    }

    fun getDefaultDateTimeFormat(): String {
        return defaultDateTimeFormat
    }

    fun setDefaultDateTimeFormat(defaultDateTimeFormat: String) {
        DateConfiguration.defaultDateTimeFormat = defaultDateTimeFormat
    }
}
