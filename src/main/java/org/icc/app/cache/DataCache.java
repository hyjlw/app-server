package org.icc.app.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.icc.app.pojo.News;
import org.icc.app.util.PropertiesLoader;

public class DataCache {
	private static DataCache cache;
	private Map<String, List<News>> map;
	private Map<String, Long> timeMap;
	
	private String latestDate = "";
	
	private DataCache() {
		map = new HashMap<String, List<News>>();
		timeMap = new HashMap<String, Long>();
	}
	
	public static DataCache getCache() {
		synchronized(DataCache.class) {
			if(cache == null) {
				cache = new DataCache();
			}
			return cache;
		}
	}
	
	public List<News> getCacheData() {
		synchronized(DataCache.class) {
			if(timeout(latestDate)) {
				map.remove(latestDate);
				timeMap.remove(latestDate);
			}
			return map.get(latestDate);
		}
	}
	
	public void cacheData(String latestDate, List<News> data) {
		synchronized(DataCache.class) {
			map.put(latestDate, data);
			timeMap.put(latestDate, new Date().getTime());
			this.latestDate = latestDate;
		}
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
