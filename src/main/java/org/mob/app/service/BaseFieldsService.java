package org.mob.app.service;

import java.util.List;
import java.util.Map;

import org.mob.app.pojo.BaseFields;
import org.mob.app.pojo.Criteria;

public interface BaseFieldsService {
	int countByExample(Criteria example);

	BaseFields selectByPrimaryKey(String fieldId);

	List<BaseFields> selectByExample(Criteria example);

	Map<String, String> selectAllByExample(Criteria example);

	/**
	 * 保存系统字段设置
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveFields(Criteria example);

	/**
	 * 删除系统字段设置
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);
}