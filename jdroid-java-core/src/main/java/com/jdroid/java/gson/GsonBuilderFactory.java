package com.jdroid.java.gson;

import com.google.gson.GsonBuilder;
import com.jdroid.java.date.DateConfiguration;

public class GsonBuilderFactory {

	public static GsonBuilder createGsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(DateConfiguration.getDefaultDateTimeFormat());
		gsonBuilder.disableHtmlEscaping();
		return gsonBuilder;
	}
}
