package org.icc.app.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.icc.app.common.springmvc.DateConvertEditor;
import org.icc.app.pojo.Article;
import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.ExceptionReturn;
import org.icc.app.pojo.ExtGridReturn;
import org.icc.app.pojo.ExtPager;
import org.icc.app.pojo.ExtReturn;
import org.icc.app.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author 
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * index
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "news/article";
	}

	/**
	 * 所有系统字段
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Object all(ExtPager pager, @RequestParam(required = false) String title) {
		try {
			Criteria criteria = new Criteria();
			// 设置分页信息
			if (pager.getLimit() != null && pager.getStart() != null) {
				criteria.setOracleEnd(pager.getLimit());
				criteria.setOracleStart(pager.getStart());
			}
			// 排序信息
			if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
				criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
			} else {
				criteria.setOrderByClause(" create_date desc, subscribe_date desc");
			}
			if (StringUtils.isNotBlank(title)) {
				criteria.put("titleLike", title);
			}
			List<Article> list = this.articleService.selectByExample(criteria);
			int total = this.articleService.countByExample(criteria);
			logger.debug("total:{}", total);
			return new ExtGridReturn(total, list);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Object save(Article article) {
		try {
			if (article == null) {
				return new ExtReturn(false, "站点不能为空！");
			}
			if (StringUtils.isBlank(article.getTitle())) {
				return new ExtReturn(false, "标题不能为空！");
			}
			if (StringUtils.isBlank(article.getContent())) {
				return new ExtReturn(false, "内容不能为空！");
			}
			if (StringUtils.isBlank(article.getWebUrl())) {
				return new ExtReturn(false, "地址不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("article", article);
			String result = this.articleService.saveArticle(criteria);
			if ("01".equals(result)) {
				return new ExtReturn(true, "保存成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "保存失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/del/{id}")
	@ResponseBody
	public Object delete(@PathVariable String id) {
		try {
			if (StringUtils.isBlank(id)) {
				return new ExtReturn(false, "主键不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("id", id);
			String result = this.articleService.delete(criteria);
			if ("01".equals(result)) {
				return new ExtReturn(true, "删除成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "删除失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
}
