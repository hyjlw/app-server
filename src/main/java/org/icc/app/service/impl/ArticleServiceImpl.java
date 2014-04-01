package org.icc.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.icc.app.dao.ArticleMapper;
import org.icc.app.pojo.Article;
import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.WebPage;
import org.icc.app.queue.CrawlerManager;
import org.icc.app.service.ArticleService;
import org.icc.app.service.WebPageService;
import org.icc.app.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private WebPageService webPageService;

	@Override
	public int countByExample(Criteria example) {
		return articleMapper.countByExample(example);
	}

	@Override
	public List<Article> selectByExample(Criteria example) {
		return articleMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveArticle(Criteria example) {
		Article article = (Article) example.get("article");
		article.setCreateDate(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		
		if(StringUtils.isEmpty(article.getPublishDate())) {
			article.setPublishDate(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		}
		
		logger.info("Save article: " + article);
		
		int result = 0;
		if(article.getId() == null) {
			result = articleMapper.insert(article);
		} else {
			result = articleMapper.updateByPrimaryKey(article);
		}
		
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		Integer id = example.getAsInteger("id");
		
		int result = articleMapper.deleteByPrimaryKey(id);
		
		return result > 0 ? "01" : "00";
	}

	@Override
	public List<Article> selectArticlesByCriteria(Criteria criteria) {
		List<Article> list = articleMapper.selectByCriteria(criteria);
		
		if(list == null) {
			list = new ArrayList<Article>();
		}
		return list;
	}

	@Override
	public void startCrawl() throws Exception {
		List<WebPage> pages = webPageService.selectAllPages();
		
		CrawlerManager.getInstance().addQueue(pages);
	}

}
