package org.icc.app.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.icc.app.common.jackjson.JackJson;
import org.icc.app.dao.BaseFieldsMapper;
import org.icc.app.pojo.BaseFields;
import org.icc.app.pojo.Criteria;
import org.icc.app.service.BaseFieldsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseFieldsServiceImpl implements BaseFieldsService {
	@Autowired
	private BaseFieldsMapper baseFieldsMapper;

	private static final Logger logger = LoggerFactory.getLogger(BaseFieldsServiceImpl.class);

	public int countByExample(Criteria example) {
		int count = this.baseFieldsMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

	public BaseFields selectByPrimaryKey(String fieldId) {
		return this.baseFieldsMapper.selectByPrimaryKey(fieldId);
	}

	public List<BaseFields> selectByExample(Criteria example) {
		return this.baseFieldsMapper.selectByExample(example);
	}

	@Override
	public Map<String, String> selectAllByExample(Criteria example) {
		List<BaseFields> list = this.baseFieldsMapper.selectByExample(example);
		Map<String, Map<String, String>> all = new HashMap<String, Map<String, String>>();
		Map<String, String> part = null;
		for (int i = 0; i < list.size(); i++) {
			BaseFields baseFields = list.get(i);
			String key = baseFields.getField();
			if (all.containsKey(key)) {
				// 如果包含这个field，就加入它的值
				part = all.get(key);
				part.put(baseFields.getValueField(), baseFields.getDisplayField());
			} else {
				part = new LinkedHashMap<String, String>();
				part.put(baseFields.getValueField(), baseFields.getDisplayField());
				// 没有这个fiel，则新加入这个filed
				all.put(key, part);
			}
		}
		part = new LinkedHashMap<String, String>();
		logger.info("开始读取系统默认配置");
		for (Entry<String, Map<String, String>> entry : all.entrySet()) {
			String key = entry.getKey();
			Map<String, String> value = entry.getValue();
			// 为了eval('(${applicationScope.fields.sex})')这个单引号使用,替换所有的'，为\'
			String val = JackJson.fromObjectToJson(value).replaceAll("\\'", "\\\\'");
			logger.info(val);
			part.put(key, val);
		}
		logger.info("结束读取系统默认配置");
		return part;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveFields(Criteria example) {
		BaseFields fields = (BaseFields) example.get("fields");

		int result = 0;
		if (fields.getFieldId() == null) {
			result = this.baseFieldsMapper.insertSelective(fields);
		} else {
			result = this.baseFieldsMapper.updateByPrimaryKeySelective(fields);
		}
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		String fieldId = example.getAsString("fieldId");
		int result = 0;
		// 删除自己
		result = this.baseFieldsMapper.deleteByPrimaryKey(fieldId);
		return result > 0 ? "01" : "00";
	}

}