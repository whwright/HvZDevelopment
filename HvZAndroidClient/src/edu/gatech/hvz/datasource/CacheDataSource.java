package edu.gatech.hvz.datasource;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple container class that serves as a temporary cache
 * for the lifetime of the application.  Keys are Strings, values are Objects.
 * All methods map to Java's Map class.
 * 
 */
public class CacheDataSource {
	private Map<String, Object> cache;
	
	public CacheDataSource() {
		cache = new HashMap<String, Object>();
	}
	
	public void put(String key, Object value) {
		cache.put(key, value);
	}

	public Object get(String key) {
		return cache.get(key);
	}
	
	public boolean containsKey(String key) {
		return cache.containsKey(key);
	}
	
	public void clear() {
		cache.clear();
	}
}
