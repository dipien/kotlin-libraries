package com.dipien.java.marshaller

import com.dipien.java.date.DateConfiguration
import com.dipien.java.date.DateUtils

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
                marshalled = com.dipien.java.utils.StringUtils.getNotEmptyString(value.toString())
            }
        }
        return marshalled
    }
}
