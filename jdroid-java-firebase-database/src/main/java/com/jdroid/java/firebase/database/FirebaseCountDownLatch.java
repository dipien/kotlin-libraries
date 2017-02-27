package com.jdroid.java.firebase.database;

import com.firebase.client.DataSnapshot;

import java.util.concurrent.CountDownLatch;

public class FirebaseCountDownLatch extends CountDownLatch {

	private DataSnapshot dataSnapshot;
	private FirebaseException firebaseException;

	public FirebaseCountDownLatch() {
		super(1);
	}

	public FirebaseException getFirebaseException() {
		return firebaseException;
	}

	public void setFirebaseException(FirebaseException firebaseException) {
		this.firebaseException = firebaseException;
	}

	public DataSnapshot getDataSnapshot() {
		return dataSnapshot;
	}

	public void setDataSnapshot(DataSnapshot dataSnapshot) {
		this.dataSnapshot = dataSnapshot;
	}
}
