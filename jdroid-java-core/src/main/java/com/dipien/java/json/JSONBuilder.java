package com.dipien.java.json;

public class JSONBuilder {
	
	private JSONStringer jsonStringer = new JSONStringer();
	
	public JSONBuilder startObject() {
		jsonStringer.object();
		return this;
	}
	
	public JSONBuilder endObject() {
		jsonStringer.endObject();
		return this;
	}
	
	public JSONBuilder startArray() {
		jsonStringer.array();
		return this;
	}
	
	public JSONBuilder startArray(String key) {
		jsonStringer.key(key);
		jsonStringer.array();
		return this;
	}
	
	public JSONBuilder endArray() {
		jsonStringer.endArray();
		return this;
	}
	
	public JSONBuilder add(String key, int value) {
		return add(key, (Integer)value);
	}
	
	public JSONBuilder add(String key, double value) {
		return add(key, (Double)value);
	}
	
	public JSONBuilder add(String key, long value) {
		return add(key, (Long)value);
	}
	
	public JSONBuilder add(String key, Object value) {
		jsonStringer.key(key);
		jsonStringer.value(value);
		return this;
	}
	
	public JSONBuilder addIfExists(String key, Object value) {
		if (value != null) {
			add(key, value);
		}
		return this;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return jsonStringer.toString();
	}
}
