package com.jdroid.java.firebase.dynamiclink.domain;

import com.jdroid.java.collections.Maps;

import java.util.Map;

public class LongDynamicLink {
	
	private String longDynamicLink;
	private Map<String, SuffixOption> suffix = Maps.newHashMap();
	
	public LongDynamicLink(String longDynamicLink, SuffixOption suffixOption) {
		this.longDynamicLink = longDynamicLink;
		this.suffix = Maps.newHashMap();
		suffix.put("option",  suffixOption);
	}
	
	public String getLongDynamicLink() {
		return longDynamicLink;
	}
	
	public Map<String, SuffixOption> getSuffix() {
		return suffix;
	}
}
