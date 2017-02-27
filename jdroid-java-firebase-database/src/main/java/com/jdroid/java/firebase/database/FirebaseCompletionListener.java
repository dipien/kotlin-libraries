package com.jdroid.java.firebase.database;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jdroid.java.exception.UnexpectedException;

public class FirebaseCompletionListener implements Firebase.CompletionListener {

	private FirebaseCountDownLatch done = new FirebaseCountDownLatch();

	@Override
	public void onComplete(FirebaseError firebaseError, Firebase firebase) {
		if (firebaseError != null) {
			done.setFirebaseException(new FirebaseException(firebaseError));
		}
		done.countDown();
	}

	public void waitOperation() {
		try {
			done.await();
			if (done.getFirebaseException() != null) {
				throw done.getFirebaseException();
			}
		} catch (InterruptedException e) {
			throw new UnexpectedException(e);
		}
	}
}
