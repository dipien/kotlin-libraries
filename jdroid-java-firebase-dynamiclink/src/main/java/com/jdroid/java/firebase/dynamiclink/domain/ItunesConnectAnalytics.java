package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.utils.StringUtils;

public class ItunesConnectAnalytics {
	
	private String at;
	private String ct;
	private String mt;
	private String pt;
	
	public String getAt() {
		return at;
	}
	
	public void setAt(String at) {
		this.at = at;
	}
	
	public String getCt() {
		return ct;
	}
	
	public void setCt(String ct) {
		this.ct = ct;
	}
	
	public String getMt() {
		return mt;
	}
	
	public void setMt(String mt) {
		this.mt = mt;
	}
	
	public String getPt() {
		return pt;
	}
	
	public void setPt(String pt) {
		this.pt = pt;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (StringUtils.isNotEmpty(at)) {
			builder.append("&at=");
			builder.append(at);
		}
		if (StringUtils.isNotEmpty(ct)) {
			builder.append("&ct=");
			builder.append(ct);
		}
		if (StringUtils.isNotEmpty(mt)) {
			builder.append("&mt=");
			builder.append(mt);
		}
		if (StringUtils.isNotEmpty(pt)) {
			builder.append("&pt=");
			builder.append(pt);
		}
		
		return builder.toString();
	}
}
