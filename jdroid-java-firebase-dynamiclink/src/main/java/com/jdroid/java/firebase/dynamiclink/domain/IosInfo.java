package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.utils.StringUtils;

public class IosInfo {
	
	private String iosBundleId;
	private String iosFallbackLink;
	private String iosCustomScheme;
	private String iosIpadFallbackLink;
	private String iosIpadBundleId;
	private String iosAppStoreId;
	
	public String getIosBundleId() {
		return iosBundleId;
	}
	
	public void setIosBundleId(String iosBundleId) {
		this.iosBundleId = iosBundleId;
	}
	
	public String getIosFallbackLink() {
		return iosFallbackLink;
	}
	
	public void setIosFallbackLink(String iosFallbackLink) {
		this.iosFallbackLink = iosFallbackLink;
	}
	
	public String getIosCustomScheme() {
		return iosCustomScheme;
	}
	
	public void setIosCustomScheme(String iosCustomScheme) {
		this.iosCustomScheme = iosCustomScheme;
	}
	
	public String getIosIpadFallbackLink() {
		return iosIpadFallbackLink;
	}
	
	public void setIosIpadFallbackLink(String iosIpadFallbackLink) {
		this.iosIpadFallbackLink = iosIpadFallbackLink;
	}
	
	public String getIosIpadBundleId() {
		return iosIpadBundleId;
	}
	
	public void setIosIpadBundleId(String iosIpadBundleId) {
		this.iosIpadBundleId = iosIpadBundleId;
	}
	
	public String getIosAppStoreId() {
		return iosAppStoreId;
	}
	
	public void setIosAppStoreId(String iosAppStoreId) {
		this.iosAppStoreId = iosAppStoreId;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (StringUtils.isNotEmpty(iosBundleId)) {
			builder.append("&ibi=");
			builder.append(iosBundleId);
		}
		if (StringUtils.isNotEmpty(iosFallbackLink)) {
			builder.append("&ifl=");
			builder.append(iosFallbackLink);
		}
		if (StringUtils.isNotEmpty(iosCustomScheme)) {
			builder.append("&ius=");
			builder.append(iosCustomScheme);
		}
		if (StringUtils.isNotEmpty(iosIpadFallbackLink)) {
			builder.append("&ipfl=");
			builder.append(iosIpadFallbackLink);
		}
		if (StringUtils.isNotEmpty(iosIpadBundleId)) {
			builder.append("&ipbi=");
			builder.append(iosIpadBundleId);
		}
		if (StringUtils.isNotEmpty(iosAppStoreId)) {
			builder.append("&isi=");
			builder.append(iosAppStoreId);
		}
		
		return builder.toString();
	}
}
