package org.icc.app.service.impl;

import java.util.List;

import org.icc.app.dao.BaseUserRoleMapper;
import org.icc.app.pojo.BaseUserRole;
import org.icc.app.pojo.Criteria;
import org.icc.app.service.BaseUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseUserRoleServiceImpl implements BaseUserRoleService {
	@Autowired
	private BaseUserRoleMapper baseUserRoleMapper;

	private static final Logger logger = LoggerFactory.getLogger(BaseUserRoleServiceImpl.class);

	public int countByExample(Criteria example) {
		int count = this.baseUserRoleMapper.countByExample(example);
		logger.debug("count: {}", count);
		return count;
	}

	public BaseUserRole selectByPrimaryKey(String userRoleId) {
		return this.baseUserRoleMapper.selectByPrimaryKey(userRoleId);
	}

	public List<BaseUserRole> selectByExample(Criteria example) {
		return this.baseUserRoleMapper.selectByExample(example);
	}

}