package org.mob.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mob.app.cache.DataCache;
import org.mob.app.common.jackjson.JackJson;
import org.mob.app.dao.NewsMapper;
import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.News;
import org.mob.app.pojo.NewsExt;
import org.mob.app.service.NewsService;
import org.mob.app.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsServiceImpl implements NewsService {
	private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
	
	@Autowired
	private NewsMapper newsMapper;

	@Override
	public int countByExample(Criteria example) {
		return newsMapper.countByExample(example);
	}

	@Override
	public List<NewsExt> selectByExample(Criteria example) {
		return newsMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveNews(Criteria example) {
		NewsExt news = (NewsExt) example.get("news");
		
		int result = 0;
		if(news.getId() == null) {
			result = newsMapper.insert(news);
		} else {
			result = newsMapper.updateByPrimaryKey(news);
		}
		
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		Integer id = example.getAsInteger("id");
		
		int result = newsMapper.deleteByPrimaryKey(id);
		
		return result > 0 ? "01" : "00";
	}

	@Override
	public List<News> selectAllNews() {
		DataCache cache = DataCache.getCache();
		
		List<News> newsList = cache.getCacheData();
		if(newsList == null || newsList.isEmpty()) {
			newsList = newsMapper.selectAllNews();
			logger.info("-------Cache news objects-------");
			cache.cacheData(DateUtil.getCurrentDate("yyyyMMdd"), newsList);
		}
		return newsList;
	}

	@Override
	public Map<String, String> selectAllParents() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> parentsMap = new HashMap<String, String>();
		
		Criteria example = new Criteria();
		example.put("parent", "null");
		
		List<NewsExt> parentNews = newsMapper.selectByExample(example);
		for(NewsExt n : parentNews) {
			parentsMap.put(n.getId(), n.getTitle());
		}
		
		String val = JackJson.fromObjectToJson(parentsMap).replaceAll("\\'", "\\\\'");
		logger.info("parents value: " + val);
		map.put("parents", val);
		
		return map;
	}

}
