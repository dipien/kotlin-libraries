package com.jdroid.java.firebase.database.auth;

import com.firebase.client.Firebase;

public abstract class CustomTokenFirebaseAuthenticationStrategy extends FirebaseAuthenticationStrategy {

	@Override
	protected void doAuthenticate(Firebase firebase) {
		firebase.authWithCustomToken(getAuthToken(), this);
	}

	protected abstract String getAuthToken();
}
