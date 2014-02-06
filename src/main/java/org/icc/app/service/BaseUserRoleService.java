package org.icc.app.service;

import java.util.List;

import org.icc.app.pojo.BaseUserRole;
import org.icc.app.pojo.Criteria;

public interface BaseUserRoleService {
	int countByExample(Criteria example);

	BaseUserRole selectByPrimaryKey(String userRoleId);

	List<BaseUserRole> selectByExample(Criteria example);

}