package com.jdroid.java.date;

import java.util.Date;

public class DateConfiguration {
	
	public static String DEFAULT_DATE_TIME_FORMAT = DateTimeFormat.YYYYMMDDHHMMSSZ;
	
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
}
