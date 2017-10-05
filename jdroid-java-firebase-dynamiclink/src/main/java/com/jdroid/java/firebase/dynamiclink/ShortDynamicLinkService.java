package com.jdroid.java.firebase.dynamiclink;

import com.jdroid.java.firebase.dynamiclink.domain.DynamicLink;
import com.jdroid.java.firebase.dynamiclink.domain.DynamicLinkResponse;
import com.jdroid.java.firebase.dynamiclink.domain.LongDynamicLink;
import com.jdroid.java.firebase.dynamiclink.domain.SuffixOption;
import com.jdroid.java.http.DefaultServer;
import com.jdroid.java.http.HttpService;
import com.jdroid.java.http.MimeType;
import com.jdroid.java.http.Server;
import com.jdroid.java.http.api.AbstractApiService;
import com.jdroid.java.http.mock.AbstractMockHttpService;
import com.jdroid.java.http.parser.json.GsonParser;
import com.jdroid.java.http.post.BodyEnclosingHttpService;

public class ShortDynamicLinkService extends AbstractApiService {

	@Override
	protected Server getServer() {
		return new DefaultServer("firebasedynamiclinks.googleapis.com/v1");
	}
	
	@Override
	protected AbstractMockHttpService getAbstractMockHttpServiceInstance(Object... urlSegments) {
		return null;
	}

	@Override
	protected Boolean isHttpMockEnabled() {
		return false;
	}

	public DynamicLinkResponse getShortDynamicLink(String webApiKey, String longDynamicLink) {
		return getShortDynamicLink(webApiKey, longDynamicLink, SuffixOption.SHORT);
	}

	public DynamicLinkResponse getShortDynamicLink(String webApiKey, String longDynamicLink, SuffixOption suffixOption) {
		BodyEnclosingHttpService service = newPostService("shortLinks");
		service.setSsl(true);
		service.addHeader(HttpService.CONTENT_TYPE_HEADER, MimeType.JSON);
		service.addQueryParameter("key", webApiKey);

		LongDynamicLink dynamicLink = new LongDynamicLink(longDynamicLink, suffixOption);

		autoMarshall(service, dynamicLink);

		return service.execute(new GsonParser(DynamicLinkResponse.class));
	}
	
	public DynamicLinkResponse createShortDynamicLink(String webApiKey, DynamicLink dynamicLink) {
		BodyEnclosingHttpService service = newPostService("shortLinks");
		service.setSsl(true);
		service.addHeader(HttpService.CONTENT_TYPE_HEADER, MimeType.JSON);
		service.addQueryParameter("key", webApiKey);

		autoMarshall(service, dynamicLink);

		return service.execute(new GsonParser(DynamicLinkResponse.class));
	}

}
