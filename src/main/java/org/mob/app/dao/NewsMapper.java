package org.mob.app.dao;

import java.util.List;

import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.News;
import org.mob.app.pojo.NewsExt;

public interface NewsMapper {

	/**
	 * 根据条件查询记录总数
	 */
	int countByExample(Criteria example);


	/**
	 * 根据主键删除记录
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(NewsExt record);

	/**
	 * 根据条件查询记录集
	 */
	List<NewsExt> selectByExample(Criteria example);
	
	List<News> selectAllNews();

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(NewsExt record);
}