package com.dipien.core.marshaller

import com.dipien.core.date.DateConfiguration
import com.dipien.core.date.DateUtils
import com.dipien.core.utils.StringUtils

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
