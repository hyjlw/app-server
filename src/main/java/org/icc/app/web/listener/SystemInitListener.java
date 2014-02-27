package org.icc.app.web.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.icc.app.common.springmvc.SpringContextHolder;
import org.icc.app.pojo.Criteria;
import org.icc.app.service.BaseFieldsService;


public class SystemInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		BaseFieldsService baseFieldsService = SpringContextHolder.getBean("baseFieldsServiceImpl");
		
		Criteria criteria = new Criteria();
		criteria.setOrderByClause(" field desc ,sort asc ");
		criteria.put("enabled", "1");
		Map<String, String> baseFieldsMap = baseFieldsService.selectAllByExample(criteria);
		
		servletContext.setAttribute("fields", baseFieldsMap);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
