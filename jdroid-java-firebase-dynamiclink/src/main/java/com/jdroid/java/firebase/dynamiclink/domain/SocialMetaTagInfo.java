package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.utils.StringUtils;

public class SocialMetaTagInfo {
	
	private String socialTitle;
	private String socialDescription;
	private String socialImageLink;
	
	public String getSocialTitle() {
		return socialTitle;
	}
	
	public void setSocialTitle(String socialTitle) {
		this.socialTitle = socialTitle;
	}
	
	public String getSocialDescription() {
		return socialDescription;
	}
	
	public void setSocialDescription(String socialDescription) {
		this.socialDescription = socialDescription;
	}
	
	public String getSocialImageLink() {
		return socialImageLink;
	}
	
	public void setSocialImageLink(String socialImageLink) {
		this.socialImageLink = socialImageLink;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (StringUtils.isNotEmpty(socialTitle)) {
			builder.append("&st=");
			builder.append(socialTitle);
		}
		if (StringUtils.isNotEmpty(socialDescription)) {
			builder.append("&sd=");
			builder.append(socialDescription);
		}
		if (StringUtils.isNotEmpty(socialImageLink)) {
			builder.append("&si=");
			builder.append(socialImageLink);
		}
		
		return builder.toString();
	}
}
