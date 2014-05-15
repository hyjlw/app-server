package org.mob.app.service;

import java.util.List;

import org.mob.app.pojo.BaseRoleModule;
import org.mob.app.pojo.Criteria;

public interface BaseRoleModuleService {
	int countByExample(Criteria example);

	BaseRoleModule selectByPrimaryKey(String roleModuleId);

	List<BaseRoleModule> selectByExample(Criteria example);
}