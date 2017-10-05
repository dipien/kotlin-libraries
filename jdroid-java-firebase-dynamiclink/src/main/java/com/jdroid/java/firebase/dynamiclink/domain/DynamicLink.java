package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.http.AbstractHttpService;

public class DynamicLink {
	
	private DynamicLinkInfo dynamicLinkInfo;
	
	public DynamicLinkInfo getDynamicLinkInfo() {
		return dynamicLinkInfo;
	}
	
	public void setDynamicLinkInfo(DynamicLinkInfo dynamicLinkInfo) {
		this.dynamicLinkInfo = dynamicLinkInfo;
	}
	
	public String build() {
		StringBuilder builder = new StringBuilder();
		builder.append(AbstractHttpService.HTTPS_PROTOCOL);
		builder.append("://");
		builder.append(dynamicLinkInfo.build());
		return builder.toString();
	}
}
