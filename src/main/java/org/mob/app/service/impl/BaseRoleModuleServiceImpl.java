package org.mob.app.service.impl;

import java.util.List;

import org.mob.app.dao.BaseRoleModuleMapper;
import org.mob.app.pojo.BaseRoleModule;
import org.mob.app.pojo.Criteria;
import org.mob.app.service.BaseRoleModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseRoleModuleServiceImpl implements BaseRoleModuleService {
	@Autowired
	private BaseRoleModuleMapper baseRoleModuleMapper;

	private static final Logger logger = LoggerFactory.getLogger(BaseRoleModuleServiceImpl.class);

	public int countByExample(Criteria example) {
		int count = this.baseRoleModuleMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

	public BaseRoleModule selectByPrimaryKey(String roleModuleId) {
		return this.baseRoleModuleMapper.selectByPrimaryKey(roleModuleId);
	}

	public List<BaseRoleModule> selectByExample(Criteria example) {
		return this.baseRoleModuleMapper.selectByExample(example);
	}
}