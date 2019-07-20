package com.jdroid.java.firebase.database;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.domain.Entity;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.firebase.database.auth.FirebaseAuthenticationStrategy;
import com.jdroid.java.repository.Repository;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FirebaseDatabaseRepository<T extends Entity> implements Repository<T> {

	private static final Logger LOGGER = LoggerUtils.getLogger(FirebaseDatabaseRepository.class);

	private FirebaseAuthenticationStrategy firebaseAuthenticationStrategy;

	public FirebaseDatabaseRepository() {
		firebaseAuthenticationStrategy = createFirebaseAuthenticationStrategy();
	}

	protected FirebaseAuthenticationStrategy createFirebaseAuthenticationStrategy() {
		return null;
	}

	protected abstract String getFirebaseUrl();

	protected abstract String getPath();

	protected abstract Class<T> getEntityClass();

	protected Firebase createFirebase() {
		Firebase firebase = new Firebase(getFirebaseUrl());
		if (firebaseAuthenticationStrategy != null && firebase.getAuth() == null) {
			firebaseAuthenticationStrategy.authenticate(firebase);
		}
		firebase = firebase.child(getPath());
		return firebase;
	}

	@Override
	public T get(String id) {
		Firebase firebase = createFirebase();
		firebase = firebase.child(id);
		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		firebase.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		T item = loadItem(listener.getDataSnapshot());
		if (item != null) {
			LOGGER.debug("[" + getPath() + "] Retrieved object from database: [" + item + "]");
		} else {
			LOGGER.debug("[" + getPath() + "] Object not found on database with id [" + id + "]");
		}
		return item;
	}

	@Override
	public void add(T item) {
		Firebase firebase = createFirebase();
		if (item.getId() != null) {
			firebase = firebase.child(item.getId());
		} else {
			firebase = firebase.push();
		}

		FirebaseCompletionListener listener = new FirebaseCompletionListener();
		firebase.setValue(item, listener);

		listener.waitOperation();
		if (item.getId() == null) {
			// Add the id field
			addIdField(firebase.getKey());
		}
		item.setId(firebase.getKey());
		LOGGER.debug("[" + getPath() + "] Stored object in database: " + item);
	}

	private void addIdField(String id) {
		Firebase firebase = createFirebase();
		firebase = firebase.child(id);

		Map<String, Object> map = new HashMap<>();
		map.put("id", id);

		FirebaseCompletionListener listener = new FirebaseCompletionListener();
		firebase.updateChildren(map, listener);
		listener.waitOperation();
	}

	@Override
	public void addAll(Collection<T> items) {
		for(T each : items) {
			add(each);
		}
	}

	@Override
	public void update(T item) {
		if (item.getId() == null) {
			throw new UnexpectedException("Item with null id can not be updated");
		}
		Firebase firebase = createFirebase().child(item.getId());
		
		FirebaseCompletionListener listener = new FirebaseCompletionListener();
		firebase.setValue(item, listener);
		listener.waitOperation();
		item.setId(firebase.getKey());
		LOGGER.debug("[" + getPath() + "] Updated object in database: " + item);
	}

	@Override
	public List<T> getByField(String fieldName, Object... values) {
		Firebase firebase = createFirebase();
		Query query = firebase.orderByChild(fieldName);

		if (values == null) {
			throw new UnexpectedException("Null value type not supported");
		} else if (values.length > 1) {
			throw new UnexpectedException("Just one value is supported");
		}
		Object value = values[0];
		if (value instanceof String) {
			query = query.equalTo((String)value);
		} else if (value instanceof Long) {
			query = query.equalTo((Long)value);
		} else if (value instanceof Double) {
			query = query.equalTo((Double)value);
		} else if (value instanceof Integer) {
			query = query.equalTo((Integer)value);
		} else if (value instanceof Boolean) {
			query = query.equalTo((Boolean)value);
		} else {
			throw new UnexpectedException("Value type not supported");
		}

		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		query.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		List<T> results = Lists.INSTANCE.newArrayList();
		for (DataSnapshot eachSnapshot: listener.getDataSnapshot().getChildren()) {
			results.add(loadItem(eachSnapshot));
		}
		LOGGER.debug("[" + getPath() + "] Retrieved objects [" + results.size() + "] from database with field [" + fieldName + "], value [" + value + "]");
		return results;
	}

	@Override
	public List<T> getAll() {
		Firebase firebase = createFirebase();
		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		firebase.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		List<T> results = Lists.INSTANCE.newArrayList();
		for (DataSnapshot eachSnapshot: listener.getDataSnapshot().getChildren()) {
			results.add(loadItem(eachSnapshot));
		}
		LOGGER.debug("[" + getPath() + "] Retrieved all objects [" + results.size() + "]");
		return results;
	}

	@Override
	public List<T> getByIds(List<String> ids) {
		Firebase firebase = createFirebase();
		FirebaseValueEventListener listener = new FirebaseValueEventListener();
		firebase.addListenerForSingleValueEvent(listener);
		listener.waitOperation();
		List<T> results = Lists.INSTANCE.newArrayList();
		for (DataSnapshot eachSnapshot: listener.getDataSnapshot().getChildren()) {
			T each = loadItem(eachSnapshot);
			if (ids.contains(each.getId())) {
				results.add(each);
			}
		}
		LOGGER.debug("[" + getPath() + "] Retrieved all objects [" + results.size() + "] with ids: " + ids);
		return results;
	}
	
	private T loadItem(DataSnapshot dataSnapshot) {
		T item = dataSnapshot.getValue(getEntityClass());
		onItemLoaded(item);
		return item;
		
	}
	
	protected void onItemLoaded(T item) {
		// Do nothing
	}
	
	@Override
	public T getItemByField(String fieldName, Object... values) {
		List<T> items = getByField(fieldName, values);
		if (!items.isEmpty()) {
			return items.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public void remove(T item) {
		remove(item.getId());
	}

	@Override
	public void removeAll() {
		innerRemove(null);
	}

	@Override
	public void removeAll(Collection<T> items) {
		for(T each : items) {
			remove(each);
		}
	}

	@Override
	public void remove(String id) {
		if (id != null) {
			innerRemove(id);
		} else {
			throw new IllegalArgumentException("The id parameter can't be null");
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
		LOGGER.debug("[" + getPath() + "] Deleted object in database with id: " + id);
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
	public void replaceAll(Collection<T> items) {
		removeAll();
		addAll(items);
	}

	@Override
	public T getUniqueInstance() {
		List<T> results = getAll();
		if (!results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}
}
