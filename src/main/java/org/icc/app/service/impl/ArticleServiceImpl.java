package org.icc.app.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.icc.app.cache.DataCache;
import org.icc.app.crawler.NetEaseCrawler;
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

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

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
		
		if(StringUtils.isEmpty(article.getSubscribeDate())) {
			article.setSubscribeDate(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
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
	public List<Article> selectAllArticles() {
		DataCache cache = DataCache.getCache();
		
		List<Article> list = cache.getCacheData();
		if(list == null || list.isEmpty()) {
			list = articleMapper.selectAll();
			logger.info("-------Cache news objects-------");
			cache.cacheData(DateUtil.getCurrentDate("yyyyMMdd"), list);
		}
		return list;
	}

	@Override
	public void startCrawl() throws Exception {
		List<WebPage> pages = webPageService.selectAllPages();
		
		CrawlerManager.getInstance().addQueue(pages);
	}

}
