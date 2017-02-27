package com.jdroid.java.repository;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.domain.Identifiable;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CacheWrapperRepository<T extends Identifiable> implements Repository<T> {

	private static final Logger LOGGER = LoggerUtils.getLogger(CacheWrapperRepository.class);

	private Repository<T> wrappedRepository;
	private Map<String, T> cache;
	private Boolean synced;

	public CacheWrapperRepository(Repository<T> wrappedRepository) {
		this.wrappedRepository = wrappedRepository;
		cache = createCacheMap();
		synced = false;
	}

	protected Map<String, T> createCacheMap() {
		return Maps.newConcurrentHashMap();
	}

	protected Map<String, T> getCache() {
		return cache;
	}

	public List<T> getCachedItems() {
		return Lists.newArrayList(cache.values());
	}

	protected Repository<T> getWrappedRepository() {
		return wrappedRepository;
	}

	protected Boolean isSynced() {
		return synced;
	}

	@Override
	public T get(String id) {
		T item = cache.get(id);
		if (item != null || synced) {
			if (item != null) {
				LOGGER.info("Retrieved object: " + item.getClass().getSimpleName() + ". [ " + item + " ]");
			}
		} else {
			item = wrappedRepository.get(id);
			if (item != null) {
				cache.put(id, item);
			}
		}
		return item;
	}

	@Override
	public void add(T item) {
		wrappedRepository.add(item);
		cache.put(item.getId(), item);
	}

	@Override
	public void addAll(Collection<T> items) {
		wrappedRepository.addAll(items);
		for (T each : items) {
			cache.put(each.getId(), each);
		}
	}

	@Override
	public void update(T item) {
		wrappedRepository.update(item);
		cache.put(item.getId(), item);
	}

	@Override
	public void remove(T item) {
		wrappedRepository.remove(item);
		cache.remove(item.getId());
	}

	@Override
	public void removeAll() {
		wrappedRepository.removeAll();
		cache.clear();
		synced = true;
	}

	@Override
	public void removeAll(Collection<T> items) {
		wrappedRepository.removeAll(items);
		for (T each : items) {
			cache.remove(each.getId());
		}
	}

	@Override
	public List<T> findByField(String fieldName, Object... values) {
		return wrappedRepository.findByField(fieldName, values);
	}

	@Override
	public List<T> getAll() {
		if (synced) {
			List<T> items = Lists.newArrayList(cache.values());
			LOGGER.info("Retrieved all objects [" + items.size() + "]");
			return items;
		} else {
			List<T> items = wrappedRepository.getAll();
			cache.clear();
			for (T each : items) {
				cache.put(each.getId(), each);
			}
			synced = true;
			return items;
		}
	}

	@Override
	public List<T> getAll(List<String> ids) {
		if (synced) {
			List<T> items = Lists.newArrayList();
			for (String each : ids) {
				items.add(cache.get(each));
			}
			LOGGER.info("Retrieved all objects [" + items.size() + "] with ids: " + ids);
			return items;
		} else {
			List<T> items = wrappedRepository.getAll(ids);
			for (T each : items) {
				cache.put(each.getId(), each);
			}
			return items;
		}
	}

	@Override
	public void remove(String id) {
		wrappedRepository.remove(id);
		cache.remove(id);
	}

	@Override
	public Boolean isEmpty() {
		if (synced) {
			return cache.isEmpty();
		} else {
			Boolean isEmpty = wrappedRepository.isEmpty();
			synced = isEmpty;
			return isEmpty;
		}
	}

	@Override
	public Long getSize() {
		if (synced) {
			return (long)cache.size();
		} else {
			Long size = wrappedRepository.getSize();
			synced = size == 0;
			return size;
		}
	}

	@Override
	public void replaceAll(Collection<T> items) {
		wrappedRepository.replaceAll(items);
		for (T each : items) {
			cache.put(each.getId(), each);
		}
	}

	@Override
	public T getUniqueInstance() {
		if (!cache.isEmpty() || synced) {
			return cache.isEmpty() ? null : cache.values().iterator().next();
		} else {
			T item = wrappedRepository.getUniqueInstance();
			if (item != null) {
				cache.put(item.getId(), item);
			}
			return item;
		}
	}

	public void clearCache() {
		cache.clear();
		synced = false;
	}
}
