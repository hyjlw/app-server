package org.icc.app.web.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.icc.app.common.springmvc.SpringContextHolder;
import org.icc.app.pojo.Criteria;
import org.icc.app.service.BaseFieldsService;
import org.icc.app.service.NewsService;
import org.icc.app.service.SiteService;


public class SystemInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		BaseFieldsService baseFieldsService = SpringContextHolder.getBean("baseFieldsServiceImpl");
		NewsService newsService = SpringContextHolder.getBean("newsServiceImpl");
		SiteService siteService = SpringContextHolder.getBean("siteServiceImpl");
		
		Criteria criteria = new Criteria();
		criteria.setOrderByClause(" field desc ,sort asc ");
		criteria.put("enabled", "1");
		Map<String, String> baseFieldsMap = baseFieldsService.selectAllByExample(criteria);
		
		Map<String, String> newsMap = newsService.selectAllParents();
		for(Map.Entry<String, String> e : newsMap.entrySet()) {
			baseFieldsMap.put(e.getKey(), e.getValue());
		}
		
		Map<String, String> sitesMap = siteService.selectAllSitesMap();
		for(Map.Entry<String, String> e : sitesMap.entrySet()) {
			baseFieldsMap.put(e.getKey(), e.getValue());
		}
		
		servletContext.setAttribute("fields", baseFieldsMap);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
