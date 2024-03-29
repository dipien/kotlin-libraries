package com.dipien.core.json;

import com.dipien.core.collections.CollectionUtils;
import com.dipien.core.marshaller.MarshallerMode;
import com.dipien.core.marshaller.MarshallerProvider;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -3869527445658873602L;

	private MarshallerMode mode;
	private Map<String, String> extras;

	public JsonMap() {
		this(MarshallerMode.COMPLETE);
	}

	public JsonMap(MarshallerMode mode) {
		this(mode, null);
	}

	public JsonMap(MarshallerMode mode, Map<String, String> extras) {
		this.mode = mode;
		this.extras = extras;
	}

	public Object put(String key, Object value, MarshallerMode mode) {
		Object marshalledValue = MarshallerProvider.get().innerMarshall(value, mode, extras);
		if ((marshalledValue != null) && !CollectionUtils.INSTANCE.isEmptyCollection(marshalledValue)) {
			super.put(key, marshalledValue);
		}
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		return put(key, value, mode);
	}

	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}
