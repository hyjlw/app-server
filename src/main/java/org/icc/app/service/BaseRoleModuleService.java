package org.icc.app.service;

import java.util.List;

import org.icc.app.pojo.BaseRoleModule;
import org.icc.app.pojo.Criteria;

public interface BaseRoleModuleService {
	int countByExample(Criteria example);

	BaseRoleModule selectByPrimaryKey(String roleModuleId);

	List<BaseRoleModule> selectByExample(Criteria example);
}