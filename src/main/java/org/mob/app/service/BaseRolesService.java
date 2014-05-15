package org.mob.app.service;

import java.util.List;

import org.mob.app.pojo.BaseRoles;
import org.mob.app.pojo.Criteria;

public interface BaseRolesService {
	int countByExample(Criteria example);

	BaseRoles selectByPrimaryKey(String roleId);

	List<BaseRoles> selectByExample(Criteria example);

	/**
	 * 保存角色
	 * 
	 * @param role
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveRole(BaseRoles role);

	/**
	 * 根据主键删除
	 * 
	 * @param criteria
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String deleteByPrimaryKey(Criteria criteria);
}