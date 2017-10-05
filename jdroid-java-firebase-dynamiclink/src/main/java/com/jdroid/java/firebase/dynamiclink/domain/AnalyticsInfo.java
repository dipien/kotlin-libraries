package com.jdroid.java.firebase.dynamiclink.domain;

public class AnalyticsInfo {
	
	private GooglePlayAnalytics googlePlayAnalytics;
	private ItunesConnectAnalytics itunesConnectAnalytics;
	
	public GooglePlayAnalytics getGooglePlayAnalytics() {
		return googlePlayAnalytics;
	}
	
	public void setGooglePlayAnalytics(GooglePlayAnalytics googlePlayAnalytics) {
		this.googlePlayAnalytics = googlePlayAnalytics;
	}
	
	public ItunesConnectAnalytics getItunesConnectAnalytics() {
		return itunesConnectAnalytics;
	}
	
	public void setItunesConnectAnalytics(ItunesConnectAnalytics itunesConnectAnalytics) {
		this.itunesConnectAnalytics = itunesConnectAnalytics;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (googlePlayAnalytics != null) {
			builder.append(googlePlayAnalytics.build());
		}
		if (itunesConnectAnalytics != null) {
			builder.append(itunesConnectAnalytics.build());
		}
		
		return builder.toString();
	}
}
