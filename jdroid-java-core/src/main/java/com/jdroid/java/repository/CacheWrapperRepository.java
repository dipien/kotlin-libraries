package com.jdroid.java.repository;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.domain.Identifiable;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CacheWrapperRepository<T extends Identifiable> implements Repository<T> {

	private static final Logger LOGGER = LoggerUtils.getLogger(CacheWrapperRepository.class);

	private Repository<T> wrappedRepository;
	private Map<String, T> cache;
	private List<String> cachedIds;
	private Boolean synced;

	public CacheWrapperRepository(Repository<T> wrappedRepository) {
		this.wrappedRepository = wrappedRepository;
		cache = createCacheMap();
		cachedIds = Lists.INSTANCE.newArrayList();
		synced = false;
	}

	protected Map<String, T> createCacheMap() {
		return Maps.INSTANCE.newConcurrentHashMap();
	}

	protected Map<String, T> getCache() {
		return cache;
	}

	public List<T> getCachedItems() {
		return Lists.INSTANCE.newArrayList(cache.values());
	}

	protected Repository<T> getWrappedRepository() {
		return wrappedRepository;
	}

	protected Boolean isSynced() {
		return synced;
	}

	@Override
	public T get(String id) {
		T item = null;
		
		if (synced && !cachedIds.contains(id)) {
			cachedIds.add(id);
		}
		
		if (cachedIds.contains(id)) {
			item = cache.get(id);
			if (item != null) {
				LOGGER.debug("Retrieved cached object: " + item.getClass().getSimpleName() + ". [ " + item + " ]");
			} else {
				LOGGER.debug("Retrieved cached object with id: " + id + ". [ " + item + " ]");
			}
		} else {
			item = wrappedRepository.get(id);
			if (item != null) {
				cache.put(id, item);
			}
			cachedIds.add(id);
		}
		return item;
	}

	@Override
	public void add(T item) {
		wrappedRepository.add(item);
		addToCache(item);
	}

	@Override
	public void addAll(Collection<T> items) {
		wrappedRepository.addAll(items);
		for (T each : items) {
			addToCache(each);
		}
	}

	@Override
	public void update(T item) {
		wrappedRepository.update(item);
		addToCache(item);
	}

	@Override
	public void remove(T item) {
		wrappedRepository.remove(item);
		removeFromCache(item);
	}

	@Override
	public void removeAll() {
		wrappedRepository.removeAll();
		cache.clear();
		cachedIds.clear();
		synced = true;
	}

	@Override
	public void removeAll(Collection<T> items) {
		wrappedRepository.removeAll(items);
		for (T each : items) {
			addToCache(each);
			removeFromCache(each);
		}
	}

	@Override
	public List<T> getByField(String fieldName, Object... values) {
		// TODO Add cache to getByField query
		LOGGER.info("The getByField query is not cached. Repository [" + wrappedRepository.getClass().getSimpleName() + "]. Field name [" + fieldName + "]");
		return wrappedRepository.getByField(fieldName, values);
	}
	
	@Override
	public T getItemByField(String fieldName, Object... values) {
		// TODO Add cache to getItemByField query
		LOGGER.info("The getItemByField query is not cached. Repository [" + wrappedRepository.getClass().getSimpleName() + "]. Field name [" + fieldName + "]");
		return wrappedRepository.getItemByField(fieldName, values);
	}
	
	@Override
	public List<T> getAll() {
		if (synced) {
			List<T> items = Lists.INSTANCE.newArrayList(cache.values());
			LOGGER.info("Retrieved all cached objects [" + items.size() + "]");
			return items;
		} else {
			List<T> items = wrappedRepository.getAll();
			cache.clear();
			cachedIds.clear();
			for (T each : items) {
				addToCache(each);
			}
			synced = true;
			return items;
		}
	}

	@Override
	public List<T> getByIds(List<String> ids) {
		if (synced) {
			List<T> items = Lists.INSTANCE.newArrayList();
			for (String each : ids) {
				items.add(cache.get(each));
			}
			LOGGER.info("Retrieved all cached objects [" + items.size() + "] with ids: " + ids);
			return items;
		} else {
			List<T> items = wrappedRepository.getByIds(ids);
			for (T each : items) {
				addToCache(each);
			}
			return items;
		}
	}
	
	private void addToCache(T each) {
		if (each.getId() == null) {
			throw new UnexpectedException("Missing item id");
		}
		cache.put(each.getId(), each);
		cachedIds.add(each.getId());
	}
	
	private void removeFromCache(T each) {
		if (each.getId() == null) {
			throw new UnexpectedException("Missing item id");
		}
		cache.remove(each.getId());
		cachedIds.remove(each.getId());
	}
	
	@Override
	public void remove(String id) {
		wrappedRepository.remove(id);
		cache.remove(id);
		cachedIds.remove(id);
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
			addToCache(each);
		}
	}

	@Override
	public T getUniqueInstance() {
		if (!cache.isEmpty() || synced) {
			return cache.isEmpty() ? null : cache.values().iterator().next();
		} else {
			T item = wrappedRepository.getUniqueInstance();
			if (item != null) {
				addToCache(item);
			}
			return item;
		}
	}

	public void clearCache() {
		cache.clear();
		cachedIds.clear();
		synced = false;
	}
}
