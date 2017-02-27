package com.jdroid.java.repository;

import com.jdroid.java.domain.Entity;

public class Pair extends Entity {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Pair{");
		sb.append("value='").append(value).append('\'');
		sb.append("} ");
		sb.append(super.toString());
		return sb.toString();
	}
}