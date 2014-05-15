package org.mob.app.service;

import java.util.List;
import java.util.Map;

import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.WebPage;

public interface WebPageService {
	int countByExample(Criteria example);

	List<WebPage> selectByExample(Criteria example);
	
	List<WebPage> selectAllPages();

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveWebPage(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);
	
	Map<String, String> selectAllPagesMap();
	
	Map<String, Boolean> selectUrlMap();
}