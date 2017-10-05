package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.utils.StringUtils;

public class GooglePlayAnalytics {
	
	private String utmSource;
	private String utmMedium;
	private String utmCampaign;
	private String utmTerm;
	private String utmContent;
	private String gclid;
	
	public String getUtmSource() {
		return utmSource;
	}
	
	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}
	
	public String getUtmMedium() {
		return utmMedium;
	}
	
	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}
	
	public String getUtmCampaign() {
		return utmCampaign;
	}
	
	public void setUtmCampaign(String utmCampaign) {
		this.utmCampaign = utmCampaign;
	}
	
	public String getUtmTerm() {
		return utmTerm;
	}
	
	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}
	
	public String getUtmContent() {
		return utmContent;
	}
	
	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}
	
	public String getGclid() {
		return gclid;
	}
	
	public void setGclid(String gclid) {
		this.gclid = gclid;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (StringUtils.isNotEmpty(utmSource)) {
			builder.append("&utm_source=");
			builder.append(utmSource);
		}
		if (StringUtils.isNotEmpty(utmMedium)) {
			builder.append("&utm_medium=");
			builder.append(utmMedium);
		}
		if (StringUtils.isNotEmpty(utmCampaign)) {
			builder.append("&utm_campaign=");
			builder.append(utmCampaign);
		}
		if (StringUtils.isNotEmpty(utmTerm)) {
			builder.append("&utm_term=");
			builder.append(utmTerm);
		}
		if (StringUtils.isNotEmpty(utmContent)) {
			builder.append("&utm_content=");
			builder.append(utmContent);
		}
		if (StringUtils.isNotEmpty(gclid)) {
			builder.append("&gclid=");
			builder.append(gclid);
		}
		
		return builder.toString();
	}
}
