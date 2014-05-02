package org.icc.app.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.icc.app.util.PropertiesLoader;

public class DataCache<T> {
	private static DataCache cache;
	private Map<String, List<T>> map;
	private Map<String, Long> timeMap;
	
	private Map<String, Boolean> urlMap;
	
	private String latestDate = "";
	
	private DataCache() {
		map = new HashMap<String, List<T>>();
		timeMap = new HashMap<String, Long>();
		
		urlMap = new HashMap<String, Boolean>();
	}
	
	public static DataCache getCache() {
		synchronized(DataCache.class) {
			if(cache == null) {
				cache = new DataCache();
			}
			return cache;
		}
	}
	
	public List<T> getCacheData() {
		synchronized(DataCache.class) {
			if(timeout(latestDate)) {
				map.remove(latestDate);
				timeMap.remove(latestDate);
			}
			return map.get(latestDate);
		}
	}
	
	public void cacheData(String latestDate, List<T> data) {
		synchronized(DataCache.class) {
			map.put(latestDate, data);
			timeMap.put(latestDate, new Date().getTime());
			this.latestDate = latestDate;
		}
	}
	
	public void cacheUrl(String url, Boolean flag) {
		synchronized(DataCache.class) {
			urlMap.put(url, flag);
		}
	}
	
	public Map<String, Boolean> getCachedUrl() {
		return urlMap;
	}
	
	private boolean timeout(String key) {
		String timeout = PropertiesLoader.getInstance().getCacheTimeout();
		
		if(timeout != null) {
			Long time = timeMap.get(key);
			if(time != null) {
				double hrs = (new Date().getTime() - time)/1000d/60/60;
				if(hrs >= Double.valueOf(timeout)) {
					return true;
				}
			}
		}
		return false;
	}
}
