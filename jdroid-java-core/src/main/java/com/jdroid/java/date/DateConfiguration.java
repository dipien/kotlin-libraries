package com.jdroid.java.date;

import java.util.Date;

public class DateConfiguration {
	
	private static String defaultDateTimeFormat = DateTimeFormat.YYYYMMDDHHMMSSZ;
	private static Date fakeNow;

	public static Date getFakeNow() {
		return fakeNow;
	}
	
	public static void setFakeNow(Date fakeNow) {
		DateConfiguration.fakeNow = fakeNow;
	}
	
	public static Boolean isFakeNow() {
		return fakeNow != null;
	}

	public static String getDefaultDateTimeFormat() {
		return defaultDateTimeFormat;
	}

	public static void setDefaultDateTimeFormat(String defaultDateTimeFormat) {
		DateConfiguration.defaultDateTimeFormat = defaultDateTimeFormat;
	}
}
