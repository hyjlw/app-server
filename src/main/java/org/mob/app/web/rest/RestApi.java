package org.mob.app.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mob.app.pojo.Article;
import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.News;
import org.mob.app.pojo.NewsType;
import org.mob.app.pojo.RestPager;
import org.mob.app.service.ArticleService;
import org.mob.app.service.NewsService;
import org.mob.app.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public class RestApi {
	@Autowired
	private NewsService newsService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private NewsTypeService newsTypeService;

	@RequestMapping(value = "/news")
	@ResponseBody
	public List<News> getNews(@RequestBody News news,
			HttpServletRequest request) {
		
		return newsService.selectAllNews();
	}
	
	@RequestMapping(value = "/articles")
	@ResponseBody
	public List<Article> getArticles(@RequestBody RestPager pager,
			HttpServletRequest request) {
		
		Criteria criteria = new Criteria();
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setOracleEnd(pager.getLimit());
			criteria.setOracleStart(pager.getStart());
		}
		criteria.setOrderByClause(" publish_date desc, create_date desc");
		if (pager.getTypeId() != null) {
			criteria.put("typeId", pager.getTypeId());
		}
		
		return articleService.selectArticlesByCriteria(criteria);
	}
	
	@RequestMapping(value = "/types")
	@ResponseBody
	public List<NewsType> getNewsTypes(@RequestBody RestPager pager,
			HttpServletRequest request) {
		return newsTypeService.selectAll();
	}
	
	@RequestMapping(value = "/getArticleById")
	@ResponseBody
	public Article getArticleById(@RequestBody Article article,
			HttpServletRequest request) {
		return articleService.getArticleById(article.getId());
	}
}
