package org.icc.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.icc.app.cache.DataCache;
import org.icc.app.common.jackjson.JackJson;
import org.icc.app.dao.WebPageMapper;
import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.WebPage;
import org.icc.app.service.WebPageService;
import org.icc.app.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WebPageServiceImpl implements WebPageService {
	private static final Logger logger = LoggerFactory.getLogger(WebPageServiceImpl.class);
	
	@Autowired
	private WebPageMapper pageMapper;

	@Override
	public int countByExample(Criteria example) {
		return pageMapper.countByExample(example);
	}

	@Override
	public List<WebPage> selectByExample(Criteria example) {
		return pageMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveWebPage(Criteria example) {
		WebPage page = (WebPage) example.get("page");
		page.setCreateDate(DateUtil.getCurrentDate("MM/dd/yyyy HH:mm:ss"));
		
		int result = 0;
		if(page.getId() == null) {
			result = pageMapper.insert(page);
		} else {
			result = pageMapper.updateByPrimaryKey(page);
		}
		
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		Integer id = example.getAsInteger("id");
		
		int result = pageMapper.deleteByPrimaryKey(id);
		
		return result > 0 ? "01" : "00";
	}

	@Override
	public List<WebPage> selectAllPages() {
		DataCache cache = DataCache.getCache();
		
		List<WebPage> list = cache.getCacheData();
		if(list == null || list.isEmpty()) {
			list = pageMapper.selectAll();
			logger.info("-------Cache news objects-------");
			cache.cacheData(DateUtil.getCurrentDate("yyyyMMdd"), list);
		}
		return list;
	}

	@Override
	public Map<String, String> selectAllPagesMap() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> parentsMap = new HashMap<String, String>();
		
		Criteria example = new Criteria();
		List<WebPage> pages = pageMapper.selectByExample(example);
		for(WebPage p : pages) {
			parentsMap.put(p.getId().toString(), p.getName());
		}
		
		String val = JackJson.fromObjectToJson(parentsMap).replaceAll("\\'", "\\\\'");
		logger.info("pages value: " + val);
		map.put("pages", val);
		
		return map;
	}

	@Override
	public Map<String, Boolean> selectUrlMap() {
		DataCache cache = DataCache.getCache();
		
		Map<String, Boolean> urlMap = cache.getCachedUrl();
		if(urlMap == null || urlMap.isEmpty()) {
			List<WebPage> list = selectAllPages();
			for(WebPage page : list) {
				cache.cacheUrl(page.getWebUrl(), true);
			}
		}
		return urlMap;
	}

}
