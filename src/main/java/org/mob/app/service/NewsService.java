package org.mob.app.service;

import java.util.List;
import java.util.Map;

import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.News;
import org.mob.app.pojo.NewsExt;

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