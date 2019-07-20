package com.jdroid.java.repository;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.domain.Identifiable;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.java.utils.ReflectionUtils;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 */
public class InMemoryRepository<T extends Identifiable> implements Repository<T> {

	private static final Logger LOGGER = LoggerUtils.getLogger(InMemoryRepository.class);

	private long nextId = 1;
	private Map<String, T> items = Maps.INSTANCE.newLinkedHashMap();

	@Override
	public void add(T item) {
		if (item.getId() == null) {
			ReflectionUtils.setId(item, nextId++);
		}
		items.put(item.getId(), item);
		LOGGER.debug("Added object in memory: " + item);
	}

	@Override
	public void addAll(Collection<T> items) {
		for (T item : items) {
			add(item);
		}
		LOGGER.debug("Stored objects in memory:\n" + items);
	}

	@Override
	public void update(T item) {
		if (item.getId() == null) {
			throw new UnexpectedException("Item with null id can not be updated");
		}
		items.put(item.getId(), item);
		LOGGER.debug("Updated object in memory: " + item);
	}

	@Override
	public void remove(T item) {
		items.remove(item.getId());
		LOGGER.debug("Deleted object from memory: " + item);
	}

	@Override
	public void removeAll(Collection<T> items) {
		for (T item : items) {
			remove(item);
		}
		LOGGER.debug("Deleted objects in memory: " + items);
	}

	@Override
	public void replaceAll(Collection<T> items) {
		removeAll();
		addAll(items);
	}

	@Override
	public List<T> getAll() {
		List<T> results = Lists.INSTANCE.newArrayList(items.values());
		LOGGER.debug("Retrieved all objects [" + results.size() + "] from memory");
		return results;
	}

	@Override
	public T get(String id) {
		return items.get(id);
	}

	@Override
	public void removeAll() {
		items.clear();
		LOGGER.debug("Deleted from memory all objects from this repository");
	}

	@Override
	public void remove(String id) {
		items.remove(id);
	}

	@Override
	public Boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public Long getSize() {
		return (long)items.size();
	}

	@Override
	public List<T> getByField(String fieldName, Object... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T getItemByField(String fieldName, Object... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> getByIds(List<String> ids) {
		List<T> itemsList = Lists.INSTANCE.newArrayList();
		for (String each : ids) {
			T item = items.get(each);
			if (item != null) {
				itemsList.add(item);
			}
		}
		return itemsList;
	}

	@Override
	public T getUniqueInstance() {
		return items.isEmpty() ? null : items.values().iterator().next();
	}
}
