package org.icc.app.dao;

import java.util.List;

import org.icc.app.pojo.Article;
import org.icc.app.pojo.Criteria;

public interface ArticleMapper {

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
	int insert(Article record);

	/**
	 * 根据条件查询记录集
	 */
	List<Article> selectByExample(Criteria example);
	
	List<Article> selectByCriteria(Criteria criteria);
	
	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(Article record);
}