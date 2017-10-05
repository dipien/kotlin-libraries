package com.jdroid.java.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;

public class MethodSerializer implements JsonSerializer<Object> {
	
	public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
		Gson gson = new Gson();
		JsonObject tree = (JsonObject)gson.toJsonTree(src);
		
		try {
			PropertyDescriptor[] properties = Introspector.getBeanInfo(src.getClass()).getPropertyDescriptors();
			for (PropertyDescriptor property : properties) {
				if (property.getReadMethod().getAnnotation(ExposeMethod.class) != null) {
					Object result = property.getReadMethod().invoke(src, (Object[])null);
					tree.add(property.getName(), gson.toJsonTree(result));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return tree;
	}
}
