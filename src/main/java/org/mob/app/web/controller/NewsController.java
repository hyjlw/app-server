package org.mob.app.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mob.app.common.springmvc.DateConvertEditor;
import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.ExceptionReturn;
import org.mob.app.pojo.ExtGridReturn;
import org.mob.app.pojo.ExtPager;
import org.mob.app.pojo.ExtReturn;
import org.mob.app.pojo.NewsExt;
import org.mob.app.service.NewsService;
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
@RequestMapping("/news")
public class NewsController {
	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	private NewsService newsService;

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
		return "news/news";
	}

	/**
	 * 所有系统字段
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Object all(ExtPager pager, @RequestParam(required = false) String name) {
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
				criteria.setOrderByClause(" id asc, title asc ");
			}
			if (StringUtils.isNotBlank(name)) {
				criteria.put("titleLike", name);
			}
			List<NewsExt> list = this.newsService.selectByExample(criteria);
			int total = this.newsService.countByExample(criteria);
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
	public Object save(NewsExt news) {
		try {
			if (news == null) {
				return new ExtReturn(false, "新闻类型不能为空！");
			}
			if (StringUtils.isBlank(news.getTitle())) {
				return new ExtReturn(false, "名称不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("news", news);
			String result = this.newsService.saveNews(criteria);
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
			String result = this.newsService.delete(criteria);
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
