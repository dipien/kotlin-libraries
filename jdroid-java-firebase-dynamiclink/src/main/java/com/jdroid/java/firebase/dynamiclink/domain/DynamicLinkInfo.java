package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.StringUtils;

public class DynamicLinkInfo {
	
	private String dynamicLinkDomain;
	private String link;
	private AndroidInfo androidInfo;
	private IosInfo iosInfo;
	private NavigationInfo navigationInfo;
	private AnalyticsInfo analyticsInfo;
	private SocialMetaTagInfo socialMetaTagInfo;
	private Suffix suffix;
	
	public String getDynamicLinkDomain() {
		return dynamicLinkDomain;
	}
	
	public void setDynamicLinkDomain(String dynamicLinkDomain) {
		this.dynamicLinkDomain = dynamicLinkDomain;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public AndroidInfo getAndroidInfo() {
		return androidInfo;
	}
	
	public void setAndroidInfo(AndroidInfo androidInfo) {
		this.androidInfo = androidInfo;
	}
	
	public IosInfo getIosInfo() {
		return iosInfo;
	}
	
	public void setIosInfo(IosInfo iosInfo) {
		this.iosInfo = iosInfo;
	}
	
	public NavigationInfo getNavigationInfo() {
		return navigationInfo;
	}
	
	public void setNavigationInfo(NavigationInfo navigationInfo) {
		this.navigationInfo = navigationInfo;
	}
	
	public AnalyticsInfo getAnalyticsInfo() {
		return analyticsInfo;
	}
	
	public void setAnalyticsInfo(AnalyticsInfo analyticsInfo) {
		this.analyticsInfo = analyticsInfo;
	}
	
	public SocialMetaTagInfo getSocialMetaTagInfo() {
		return socialMetaTagInfo;
	}
	
	public void setSocialMetaTagInfo(SocialMetaTagInfo socialMetaTagInfo) {
		this.socialMetaTagInfo = socialMetaTagInfo;
	}
	
	public Suffix getSuffix() {
		return suffix;
	}
	
	public void setSuffix(Suffix suffix) {
		this.suffix = suffix;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (dynamicLinkDomain == null) {
			throw new UnexpectedException("Missing domain when building Firebase dynamic link");
		}
		builder.append(dynamicLinkDomain);
		
		if (StringUtils.isEmpty(link)) {
			throw new UnexpectedException("Missing link when building Firebase dynamic link");
		}
		builder.append("/?link=");
		builder.append(link);
		
		if (analyticsInfo != null) {
			builder.append(androidInfo.build());
		}
		if (iosInfo != null) {
			builder.append(iosInfo.build());
		}
		if (navigationInfo != null) {
			builder.append(navigationInfo.build());
		}
		if (analyticsInfo != null) {
			builder.append(analyticsInfo.build());
		}
		if (socialMetaTagInfo != null) {
			builder.append(socialMetaTagInfo.build());
		}
		
		return builder.toString();
	}
}