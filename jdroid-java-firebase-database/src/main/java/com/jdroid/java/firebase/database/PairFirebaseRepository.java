package com.jdroid.java.firebase.database;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.firebase.database.auth.FirebaseAuthenticationStrategy;
import com.jdroid.java.repository.Pair;
import com.jdroid.java.repository.PairRepository;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;

public abstract class PairFirebaseRepository implements PairRepository {

	private static final Logger LOGGER = LoggerUtils.getLogger(PairFirebaseRepository.class);

	private FirebaseAuthenticationStrategy firebaseAuthenticationStrategy;

	public PairFirebaseRepository() {
		firebaseAuthenticationStrategy = createFirebaseAuthenticationStrategy();
	}

	protected FirebaseAuthenticationStrategy createFirebaseAuthenticationStrategy() {
		return null;
	}

	protected abstract String getFirebaseUrl();

	protected abstract String getPath();

	protected Firebase createFirebase() {
		Firebase firebase = new Firebase(getFirebaseUrl());
		if (firebaseAuthenticationStrategy != null && firebase.getAuth() == null) {
			firebaseAuthenticationStrategy.authenticate(firebase);
		}
		firebase = firebase.child(getPath());
		return firebase;
	}

	@Override
	public Pair get(String id) {
		Firebase firebase = createFirebase();
		firebase = firebase.child(id);
		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		firebase.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		String result = listener.getDataSnapshot().getValue(String.class);
		Pair pair = null;
		if (result != null) {
			pair = new Pair();
			pair.setId(id);
			pair.setValue(result);
			LOGGER.debug("Retrieved object from database with path [ " + getPath() + "]. [ " + result + " ]");
		} else {
			LOGGER.debug("Object not found on database with path [ " + getPath() + " ] and id [ " + id + " ]");
		}
		return pair;
	}

	@Override
	public void add(Pair item) {
		Firebase firebase = createFirebase();
		firebase = firebase.child(item.getId());

		FirebaseCompletionListener listener = new FirebaseCompletionListener();
		firebase.setValue(item.getValue(), listener);

		listener.waitOperation();
		LOGGER.debug("Stored object in database: " + item);
	}

	@Override
	public void addAll(Collection<Pair> items) {
		for (Pair each : items) {
			add(each);
		}
	}

	@Override
	public void update(Pair item) {
		if (item.getId() == null) {
			throw new UnexpectedException("Item with null id can not be updated");
		}

		Firebase firebase = createFirebase();
		firebase = firebase.child(item.getId());

		FirebaseCompletionListener listener = new FirebaseCompletionListener();
		firebase.setValue(item.getValue(), listener);

		listener.waitOperation();
		LOGGER.debug("Updated object in database: " + item);
	}

	@Override
	public List<Pair> getByField(String fieldName, Object... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Pair getItemByField(String fieldName, Object... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Pair> getAll() {
		Firebase firebase = createFirebase();
		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		firebase.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		List<Pair> results = Lists.INSTANCE.newArrayList();
		for (DataSnapshot eachSnapshot : listener.getDataSnapshot().getChildren()) {
			Pair pair = new Pair();
			pair.setId(eachSnapshot.getKey());
			pair.setValue(eachSnapshot.getValue(String.class));
			results.add(pair);
		}
		LOGGER.debug("Retrieved all objects [" + results.size() + "] from path: " + getPath());
		return results;
	}

	@Override
	public List<Pair> getByIds(List<String> ids) {
		Firebase firebase = createFirebase();
		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		firebase.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		List<Pair> results = Lists.INSTANCE.newArrayList();
		for (DataSnapshot eachSnapshot : listener.getDataSnapshot().getChildren()) {
			Pair pair = new Pair();
			pair.setId(eachSnapshot.getKey());
			pair.setValue(eachSnapshot.getValue(String.class));
			if (ids.contains(pair.getId())) {
				results.add(pair);
			}
		}
		LOGGER.debug("Retrieved all objects [" + results.size() + "] from path: " + getPath() + " and ids: " + ids);
		return results;
	}


	@Override
	public void remove(Pair item) {
		remove(item.getId());
	}

	@Override
	public void removeAll() {
		innerRemove(null);
	}

	@Override
	public void removeAll(Collection<Pair> items) {
		for (Pair each : items) {
			remove(each);
		}
	}

	@Override
	public void remove(String id) {
		if (id != null) {
			innerRemove(id);
		}
	}

	private void innerRemove(String id) {
		Firebase firebase = createFirebase();
		if (id != null) {
			firebase = firebase.child(id);
		}

		FirebaseCompletionListener listener = new FirebaseCompletionListener();
		firebase.removeValue(listener);
		listener.waitOperation();
		LOGGER.debug("Deleted object in database: with id: " + id);
	}

	@Override
	public Boolean isEmpty() {
		return getSize() == 0;
	}

	@Override
	public Long getSize() {
		Firebase firebase = createFirebase();
		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		firebase.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		return listener.getDataSnapshot().getChildrenCount();
	}

	@Override
	public void replaceAll(Collection<Pair> items) {
		for (Pair each : items) {
			update(each);
		}
	}

	@Override
	public Pair getUniqueInstance() {
		List<Pair> results = getAll();
		if (!results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}
}
