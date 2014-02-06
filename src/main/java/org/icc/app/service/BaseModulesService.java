package org.icc.app.service;

import java.util.HashMap;
import java.util.List;

import org.icc.app.pojo.BaseModules;
import org.icc.app.pojo.BaseUsers;
import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.Tree;

public interface BaseModulesService {
	int countByExample(Criteria example);

	BaseModules selectByPrimaryKey(Integer moduleId);

	List<BaseModules> selectByExample(Criteria example);

	/**
	 * 查找用户的模块
	 * 
	 * @param baseUser
	 * @return 00：失败，01：成功 ,其他情况
	 */
	Tree selectModulesByUser(BaseUsers baseUser);

	/**
	 * 查找用户的模块
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	Tree selectAllModules(Criteria example);

	/**
	 * 保存角色的系统菜单
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveModule(Criteria example);

	/**
	 * 保存系统菜单
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveModules(Criteria example);

	/**
	 * 删除系统菜单
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);

	/**
	 * 动态sql<br>
	 * 最好不要带外部参数拼装进来，防止SQL注入<br>
	 * 非正常情况不要用
	 * 
	 * @param example
	 * @return
	 */
	List<HashMap<String, Object>> selectByDynamicSql(Criteria example);
}