package com.jdroid.java.firebase.database;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jdroid.java.exception.UnexpectedException;

public class FirebaseValueEventListener implements ValueEventListener {

	private FirebaseCountDownLatch done = new FirebaseCountDownLatch();

	@Override
	public void onDataChange(DataSnapshot snapshot) {
		done.setDataSnapshot(snapshot);
		done.countDown();
	}

	@Override
	public void onCancelled(FirebaseError firebaseError) {
		done.setFirebaseException(new com.jdroid.java.firebase.database.FirebaseException(firebaseError));
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

	public DataSnapshot getDataSnapshot() {
		return done.getDataSnapshot();
	}

}
