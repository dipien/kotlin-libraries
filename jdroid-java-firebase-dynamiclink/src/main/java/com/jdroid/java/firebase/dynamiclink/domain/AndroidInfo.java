package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.utils.StringUtils;

public class AndroidInfo {
	
	private String androidPackageName;
	private String androidFallbackLink;
	private String androidMinPackageVersionCode;
	private String androidLink;
	
	public String getAndroidPackageName() {
		return androidPackageName;
	}
	
	public void setAndroidPackageName(String androidPackageName) {
		this.androidPackageName = androidPackageName;
	}
	
	public String getAndroidFallbackLink() {
		return androidFallbackLink;
	}
	
	public void setAndroidFallbackLink(String androidFallbackLink) {
		this.androidFallbackLink = androidFallbackLink;
	}
	
	public String getAndroidMinPackageVersionCode() {
		return androidMinPackageVersionCode;
	}
	
	public void setAndroidMinPackageVersionCode(Integer androidMinPackageVersionCode) {
		this.androidMinPackageVersionCode = androidMinPackageVersionCode.toString();
	}
	
	public String getAndroidLink() {
		return androidLink;
	}
	
	public void setAndroidLink(String androidLink) {
		this.androidLink = androidLink;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (StringUtils.isNotEmpty(androidPackageName)) {
			builder.append("&apn=");
			builder.append(androidPackageName);
		}
		if (StringUtils.isNotEmpty(androidMinPackageVersionCode)) {
			builder.append("&amv=");
			builder.append(androidMinPackageVersionCode);
		}
		if (StringUtils.isNotEmpty(androidFallbackLink)) {
			builder.append("&afl=");
			builder.append(androidFallbackLink);
		}
		if (StringUtils.isNotEmpty(androidLink)) {
			builder.append("&al=");
			builder.append(androidLink);
		}
		
		return builder.toString();
	}
}
