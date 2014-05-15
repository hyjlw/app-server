package org.mob.app.service;

import java.util.List;
import java.util.Map;

import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.Site;

public interface SiteService {
	int countByExample(Criteria example);

	List<Site> selectByExample(Criteria example);
	
	List<Site> selectAllSites();

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveSite(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);
	
	Map<String, String> selectAllSitesMap();

}