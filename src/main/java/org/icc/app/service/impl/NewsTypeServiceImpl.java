package org.icc.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.icc.app.cache.DataCache;
import org.icc.app.common.jackjson.JackJson;
import org.icc.app.dao.NewsTypeMapper;
import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.NewsType;
import org.icc.app.service.NewsTypeService;
import org.icc.app.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsTypeServiceImpl implements NewsTypeService {
	private static final Logger logger = LoggerFactory.getLogger(NewsTypeServiceImpl.class);
	
	@Autowired
	private NewsTypeMapper typeMapper;

	@Override
	public int countByExample(Criteria example) {
		return typeMapper.countByExample(example);
	}

	@Override
	public List<NewsType> selectByExample(Criteria example) {
		return typeMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveNewsType(Criteria example) {
		NewsType type = (NewsType) example.get("type");
		type.setCreateDate(DateUtil.getCurrentDate("MM/dd/yyyy HH:mm:ss"));
		
		int result = 0;
		if(type.getId() == null) {
			result = typeMapper.insert(type);
		} else {
			result = typeMapper.updateByPrimaryKey(type);
		}
		
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		Integer id = example.getAsInteger("id");
		
		int result = typeMapper.deleteByPrimaryKey(id);
		
		return result > 0 ? "01" : "00";
	}

	@Override
	public List<NewsType> selectAll() {
		DataCache cache = DataCache.getCache();
		
		List<NewsType> typesList = cache.getCacheData();
		if(typesList == null || typesList.isEmpty()) {
			typesList = typeMapper.selectAll();
			logger.info("-------Cache news objects-------");
			cache.cacheData(DateUtil.getCurrentDate("yyyyMMdd"), typesList);
		}
		return typesList;
	}

	@Override
	public Map<String, String> selectAllTypesMap() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> parentsMap = new HashMap<String, String>();
		
		Criteria example = new Criteria();
		List<NewsType> types = typeMapper.selectByExample(example);
		for(NewsType t : types) {
			parentsMap.put(t.getId().toString(), t.getName());
		}
		
		String val = JackJson.fromObjectToJson(parentsMap).replaceAll("\\'", "\\\\'");
		logger.info("sites value: " + val);
		map.put("sites", val);
		
		return map;
	}

}
