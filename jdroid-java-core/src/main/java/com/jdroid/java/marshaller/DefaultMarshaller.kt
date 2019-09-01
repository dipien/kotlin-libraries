package com.jdroid.java.marshaller

import com.jdroid.java.date.DateConfiguration
import com.jdroid.java.date.DateUtils
import com.jdroid.java.utils.StringUtils

import java.util.Date

class DefaultMarshaller : Marshaller<Any, Any> {

    override fun marshall(value: Any?, mode: MarshallerMode?, extras: Map<String, String>?): Any? {
        var marshalled: Any? = null
        if (value != null) {
            if (value is Boolean) {
                marshalled = value
            } else if (value is Date) {
                marshalled = DateUtils.format(value as Date?, DateConfiguration.getDefaultDateTimeFormat())
            } else if (value is Number) {
                marshalled = value
            } else {
                marshalled = StringUtils.getNotEmptyString(value.toString())
            }
        }
        return marshalled
    }
}
