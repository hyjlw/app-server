package org.mob.app.service;

import java.util.List;

import org.mob.app.pojo.BaseUserRole;
import org.mob.app.pojo.Criteria;

public interface BaseUserRoleService {
	int countByExample(Criteria example);

	BaseUserRole selectByPrimaryKey(String userRoleId);

	List<BaseUserRole> selectByExample(Criteria example);

}