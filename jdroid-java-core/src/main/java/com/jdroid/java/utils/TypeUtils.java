package com.jdroid.java.utils;

import com.jdroid.java.exception.UnexpectedException;

@Deprecated
public class TypeUtils {
	
	// Float

	public static Float getFloat(Object value) {
		return getFloat(value, null);
	}
	
	public static Float getFloat(String value) {
		return getFloat(value, null);
	}
	
	public static Float getFloat(String value, Float defaultValue) {
		return StringUtils.isNotEmpty(value) ? Float.valueOf(value) : defaultValue;
	}
	
	public static Float getFloat(Object value, Float defaultValue) {
		if (value != null) {
			if (value instanceof Float) {
				return (Float)value;
			} else {
				return getFloat(value.toString(), defaultValue);
			}
		} else {
			return defaultValue;
		}
	}

	public static Float getSafeFloat(String value) {
		return getSafeFloat(value, null);
	}
	
	public static Float getSafeFloat(Object value) {
		return getSafeFloat(value, null);
	}

	public static Float getSafeFloat(Object value, Float defaultValue) {
		try {
			return getFloat(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Float getSafeFloat(String value, Float defaultValue) {
		return getSafeFloat((Object)value, defaultValue);
	}
	
	// Integer
	
	public static Integer getInteger(Object value) {
		return getInteger(value, null);
	}
	
	public static Integer getInteger(String value) {
		return getInteger(value, null);
	}
	
	public static Integer getInteger(String value, Integer defaultValue) {
		return StringUtils.isNotEmpty(value) ? Integer.valueOf(value) : defaultValue;
	}
	
	public static Integer getInteger(Object value, Integer defaultValue) {
		if (value != null) {
			if (value instanceof Integer) {
				return (Integer)value;
			} else {
				return getInteger(value.toString(), defaultValue);
			}
		} else {
			return defaultValue;
		}
	}
	
	public static Integer getSafeInteger(String value) {
		return getSafeInteger(value, null);
	}
	
	public static Integer getSafeInteger(Object value) {
		return getSafeInteger(value, null);
	}
	
	public static Integer getSafeInteger(Object value, Integer defaultValue) {
		try {
			return getInteger(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Integer getSafeInteger(String value, Integer defaultValue) {
		return getSafeInteger((Object)value, defaultValue);
	}
	
	// Long
	
	public static Long getLong(Object value) {
		return getLong(value, null);
	}
	
	public static Long getLong(String value) {
		return getLong(value, null);
	}
	
	public static Long getLong(String value, Long defaultValue) {
		return StringUtils.isNotEmpty(value) ? Long.valueOf(value) : defaultValue;
	}
	
	public static Long getLong(Object value, Long defaultValue) {
		if (value != null) {
			if (value instanceof Long) {
				return (Long)value;
			} else {
				return getLong(value.toString(), defaultValue);
			}
		} else {
			return defaultValue;
		}
	}
	
	public static Long getSafeLong(String value) {
		return getSafeLong(value, null);
	}
	
	public static Long getSafeLong(Object value) {
		return getSafeLong(value, null);
	}
	
	public static Long getSafeLong(Object value, Long defaultValue) {
		try {
			return getLong(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Long getSafeLong(String value, Long defaultValue) {
		return getSafeLong((Object)value, defaultValue);
	}
	
	// Double
	
	public static Double getDouble(Object value) {
		return getDouble(value, null);
	}
	
	public static Double getDouble(String value) {
		return getDouble(value, null);
	}
	
	public static Double getDouble(String value, Double defaultValue) {
		return StringUtils.isNotEmpty(value) ? Double.valueOf(value) : defaultValue;
	}
	
	public static Double getDouble(Object value, Double defaultValue) {
		if (value != null) {
			if (value instanceof Double) {
				return (Double)value;
			} else {
				return getDouble(value.toString(), defaultValue);
			}
		} else {
			return defaultValue;
		}
	}
	
	public static Double getSafeDouble(String value) {
		return getSafeDouble(value, null);
	}
	
	public static Double getSafeDouble(Object value) {
		return getSafeDouble(value, null);
	}
	
	public static Double getSafeDouble(Object value, Double defaultValue) {
		try {
			return getDouble(value, defaultValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double getSafeDouble(String value, Double defaultValue) {
		return getSafeDouble((Object)value, defaultValue);
	}
	
	// Boolean

	public static Boolean getBooleanFromNumber(String value) {
		return StringUtils.isNotEmpty(value) ? "1".equals(value) : null;
	}

	public static Boolean getBoolean(Object value) {
		return getBoolean(value, null);
	}
	
	public static Boolean getBoolean(String value) {
		return getBoolean(value, null);
	}

	public static Boolean getBoolean(String value, Boolean defaultValue) {
		if (StringUtils.isNotEmpty(value)) {
			if (value.equalsIgnoreCase("true")) {
				return true;
			} else if (value.equalsIgnoreCase("false")) {
				return false;
			} else {
				throw new UnexpectedException("Invalid Boolean value: " + value);
			}
		} else {
			return defaultValue;
		}
	}
	
	public static Boolean getBoolean(Object value, Boolean defaultValue) {
		if (value != null) {
			if (value instanceof Boolean) {
				return (Boolean)value;
			} else {
				return getBoolean(value.toString(), defaultValue);
			}
		} else {
			return defaultValue;
		}
	}

	public static Boolean getSafeBoolean(String value) {
		return getSafeBoolean(value, null);
	}
	
	public static Boolean getSafeBoolean(Object value) {
		return getSafeBoolean(value, null);
	}
	
	public static Boolean getSafeBoolean(Object value, Boolean defaultValue) {
		try {
			return getBoolean(value, defaultValue);
		} catch (UnexpectedException e) {
			return null;
		}
	}
	
	public static Boolean getSafeBoolean(String value, Boolean defaultValue) {
		return getSafeBoolean((Object)value, defaultValue);
	}
	
	// String

	public static String getString(Object value) {
		return value != null ? value.toString() : null;
	}
}
