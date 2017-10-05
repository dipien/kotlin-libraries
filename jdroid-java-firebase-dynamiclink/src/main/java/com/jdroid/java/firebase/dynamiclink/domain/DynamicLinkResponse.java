package com.jdroid.java.firebase.dynamiclink.domain;

public class DynamicLinkResponse {
	
	private String shortLink;
	private String previewLink;
	
	public String getShortLink() {
		return shortLink;
	}
	
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	
	public String getPreviewLink() {
		return previewLink;
	}
	
	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}
}
