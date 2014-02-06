package org.icc.app.service;

import java.util.List;
import java.util.Map;

import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.News;
import org.icc.app.pojo.NewsExt;

public interface NewsService {
	int countByExample(Criteria example);

	List<NewsExt> selectByExample(Criteria example);
	
	List<News> selectAllNews();

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveNews(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);
	
	Map<String, String> selectAllParents();

}