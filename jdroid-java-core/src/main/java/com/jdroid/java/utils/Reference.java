package com.jdroid.java.utils;

import java.io.Serializable;

public class Reference<T> implements Serializable {
	
	private T value;
	
	public Reference() {
	}
	
	public Reference(T value) {
		this.value = value;
	}
	
	public T get() {
		return this.value;
	}
	
	public void set(T value) {
		this.value = value;
	}
}