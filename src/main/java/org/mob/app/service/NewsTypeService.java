package org.mob.app.service;

import java.util.List;
import java.util.Map;

import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.NewsType;

public interface NewsTypeService {
	int countByExample(Criteria example);

	List<NewsType> selectByExample(Criteria example);
	
	List<NewsType> selectAll();

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveNewsType(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);
	
	Map<String, String> selectAllTypesMap();

}