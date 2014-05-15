package org.mob.app.service;

import java.util.List;

import org.mob.app.pojo.Article;
import org.mob.app.pojo.Criteria;

public interface ArticleService {
	int countByExample(Criteria example);

	List<Article> selectByExample(Criteria example);
	
	List<Article> selectArticlesByCriteria(Criteria criteria);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveArticle(Criteria example);
	
	String saveArticleImage(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);

	void startCrawl() throws Exception;
	
	Article getArticleById(Integer id);
}