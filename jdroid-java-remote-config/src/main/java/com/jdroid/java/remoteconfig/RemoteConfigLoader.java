package com.jdroid.java.remoteconfig;

import java.util.List;

public interface RemoteConfigLoader {
	
	void fetch();
	
	Object getObject(RemoteConfigParameter remoteConfigParameter);
	
	String getString(RemoteConfigParameter remoteConfigParameter);

	List<String> getStringList(RemoteConfigParameter remoteConfigParameter);
	
	Boolean getBoolean(RemoteConfigParameter remoteConfigParameter);
	
	Long getLong(RemoteConfigParameter remoteConfigParameter);
	
	Double getDouble(RemoteConfigParameter remoteConfigParameter);
	
}
