package com.jdroid.java.firebase.dynamiclink.domain;

public class NavigationInfo {
	
	private Boolean enableForcedRedirect;
	
	public Boolean getEnableForcedRedirect() {
		return enableForcedRedirect;
	}
	
	public void setEnableForcedRedirect(Boolean enableForcedRedirect) {
		this.enableForcedRedirect = enableForcedRedirect;
	}
	
	String build() {
		StringBuilder builder = new StringBuilder();
		
		if (enableForcedRedirect != null) {
			builder.append("&efr=");
			builder.append(enableForcedRedirect);
		}
		
		return builder.toString();
	}
}
