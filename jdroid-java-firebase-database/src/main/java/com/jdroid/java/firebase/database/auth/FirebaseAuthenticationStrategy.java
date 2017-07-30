package com.jdroid.java.firebase.database.auth;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.firebase.database.FirebaseDatabaseRepository;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;

public abstract class FirebaseAuthenticationStrategy implements Firebase.AuthResultHandler {

	private static final Logger LOGGER = LoggerUtils.getLogger(FirebaseDatabaseRepository.class);

	private CountDownLatch countDownLatch;

	public final void authenticate(Firebase firebase) {
		countDownLatch = new CountDownLatch(1);
		doAuthenticate(firebase);
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new UnexpectedException(e);
		}
	}

	protected abstract void doAuthenticate(Firebase firebase);

	protected void doOnAuthenticationError(FirebaseError error) {
		throw error.toException();
	}

	protected void doOnAuthenticated(AuthData authData) {
		// Do nothing
	}

	@Override
	public final void onAuthenticationError(FirebaseError error) {
		getCountDownLatch().countDown();
		doOnAuthenticationError(error);

	}
	@Override
	public final void onAuthenticated(AuthData authData) {
		getCountDownLatch().countDown();
		LOGGER.info("Firebase authenticated. " + authData.toString());
		doOnAuthenticated(authData);
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}
}
