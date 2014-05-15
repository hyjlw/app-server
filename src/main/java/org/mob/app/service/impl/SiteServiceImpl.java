package org.mob.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mob.app.cache.DataCache;
import org.mob.app.common.jackjson.JackJson;
import org.mob.app.dao.SiteMapper;
import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.Site;
import org.mob.app.service.SiteService;
import org.mob.app.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SiteServiceImpl implements SiteService {
	private static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);
	
	@Autowired
	private SiteMapper sitesMapper;

	@Override
	public int countByExample(Criteria example) {
		return sitesMapper.countByExample(example);
	}

	@Override
	public List<Site> selectByExample(Criteria example) {
		return sitesMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveSite(Criteria example) {
		Site site = (Site) example.get("site");
		site.setCreateDate(DateUtil.getCurrentDate("MM/dd/yyyy HH:mm:ss"));
		
		int result = 0;
		if(site.getId() == null) {
			result = sitesMapper.insert(site);
		} else {
			result = sitesMapper.updateByPrimaryKey(site);
		}
		
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		Integer id = example.getAsInteger("id");
		
		int result = sitesMapper.deleteByPrimaryKey(id);
		
		return result > 0 ? "01" : "00";
	}

	@Override
	public List<Site> selectAllSites() {
		DataCache cache = DataCache.getCache();
		
		List<Site> newsList = cache.getCacheData();
		if(newsList == null || newsList.isEmpty()) {
			newsList = sitesMapper.selectAll();
			logger.info("-------Cache news objects-------");
			cache.cacheData(DateUtil.getCurrentDate("yyyyMMdd"), newsList);
		}
		return newsList;
	}

	@Override
	public Map<String, String> selectAllSitesMap() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> parentsMap = new HashMap<String, String>();
		
		Criteria example = new Criteria();
		List<Site> sites = sitesMapper.selectByExample(example);
		for(Site s : sites) {
			parentsMap.put(s.getId().toString(), s.getName());
		}
		
		String val = JackJson.fromObjectToJson(parentsMap).replaceAll("\\'", "\\\\'");
		logger.info("sites value: " + val);
		map.put("sites", val);
		
		return map;
	}

}
