package com.jdroid.java.firebase.database;

import com.firebase.client.FirebaseError;
import com.jdroid.java.exception.AbstractException;

public class FirebaseException extends AbstractException {

	private FirebaseError firebaseError;

	public FirebaseException(FirebaseError firebaseError) {
		super(firebaseError.getMessage());
		this.firebaseError = firebaseError;
	}

	public FirebaseError getFirebaseError() {
		return firebaseError;
	}
}
