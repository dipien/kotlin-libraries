package com.jdroid.java.marshaller;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.json.JSONArray;
import com.jdroid.java.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarshallerProvider {

	private Map<Class<?>, Marshaller<Object, Object>> marshallers = new HashMap();
	private Marshaller<Object, Object> defaultMarshaller = new DefaultMarshaller();
	private MarshallerTypeEvaluator marshallerTypeEvaluator = new DefaultMarshallerTypeEvaluator();

	private static final MarshallerProvider INSTANCE = new MarshallerProvider();

	private MarshallerProvider() {
		// singleton
	}

	/**
	 * @return The instance of this provider
	 */
	public static MarshallerProvider get() {
		return MarshallerProvider.INSTANCE;
	}

	public Object marshall(Object value, MarshallerMode mode, Map<String, String> extras) {
		if (value != null) {
			if (value instanceof Collection<?>) {
				Collection<?> collection = (Collection<?>)value;
				List<Object> list = Lists.INSTANCE.newArrayList();

				for (Object each : collection) {
					list.add(innerMarshall(each, mode, extras));
				}
				return new JSONArray(list);
			} else {
				return innerMarshall(value, mode, extras);
			}
		} else {
			return null;
		}
	}

	public Object innerMarshall(Object value, MarshallerMode mode, Map<String, String> extras) {
		if (value != null) {
			if (value instanceof Collection<?>) {
				Collection<?> collection = (Collection<?>)value;
				List<Object> list = Lists.INSTANCE.newArrayList();

				for (Object each : collection) {
					list.add(innerMarshall(each, mode, extras));
				}
				return list;
			} else if (value instanceof Map) {
				Map<Object, Object> map = (Map)value;
				if (map.isEmpty()) {
					return null;
				} else {
					JSONObject jsonObject = new JSONObject();
					for (Map.Entry entry : map.entrySet()) {
						jsonObject.put(entry.getKey().toString(), innerMarshall(entry.getValue(), mode, extras));
					}
					return jsonObject;
				}
			} else {
				return getMarshaller(value).marshall(value, mode, extras);
			}
		} else {
			return null;
		}
	}

	public Marshaller<Object, Object> getMarshaller(Object marshallerType) {
		Class<?> clazz = marshallerTypeEvaluator.evaluate(marshallerType);
		return marshallers.containsKey(clazz) ? marshallers.get(clazz) : defaultMarshaller;
	}

	public void setMarshallers(Map<Class<?>, Marshaller<Object, Object>> marshallers) {
		this.marshallers = marshallers;
	}

	@SuppressWarnings("unchecked")
	public void addMarshaller(Class<?> clazz, Marshaller<? extends Object, ? extends Object> marshaller) {
		marshallers.put(clazz, (Marshaller<Object, Object>)marshaller);
	}

	public interface MarshallerTypeEvaluator {

		Class<?> evaluate(Object marshallerType);
	}

	private class DefaultMarshallerTypeEvaluator implements MarshallerTypeEvaluator {

		@Override
		public Class<?> evaluate(Object marshallerType) {
			return marshallerType.getClass();
		}
	}

	public void setMarshallerTypeEvaluator(MarshallerTypeEvaluator marshallerTypeEvaluator) {
		this.marshallerTypeEvaluator = marshallerTypeEvaluator;
	}

	public void setDefaultMarshaller(Marshaller<Object, Object> defaultMarshaller) {
		this.defaultMarshaller = defaultMarshaller;
	}
}
