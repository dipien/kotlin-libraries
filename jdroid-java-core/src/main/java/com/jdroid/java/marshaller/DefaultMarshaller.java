package com.jdroid.java.marshaller;

import com.jdroid.java.date.DateConfiguration;
import com.jdroid.java.date.DateUtils;
import com.jdroid.java.utils.StringUtils;

import java.util.Date;
import java.util.Map;

public class DefaultMarshaller implements Marshaller<Object, Object> {
	
	/**
	 * @see com.jdroid.java.marshaller.Marshaller#marshall(java.lang.Object, com.jdroid.java.marshaller.MarshallerMode,
	 *      java.util.Map)
	 */
	@Override
	public Object marshall(Object object, MarshallerMode mode, Map<String, String> extras) {
		Object marshalled = null;
		if (object != null) {
			if (object instanceof Boolean) {
				marshalled = object;
			} else if (object instanceof Date) {
				marshalled = DateUtils.format((Date)object, DateConfiguration.getDefaultDateTimeFormat());
			} else if (object instanceof Number) {
				marshalled = object;
			} else {
				marshalled = StringUtils.getNotEmptyString(object.toString());
			}
		}
		return marshalled;
	}
}
