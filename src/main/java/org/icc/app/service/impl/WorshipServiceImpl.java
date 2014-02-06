package org.icc.app.service.impl;

import java.util.List;

import org.icc.app.dao.WorshipsMapper;
import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.Worship;
import org.icc.app.service.WorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorshipServiceImpl implements WorshipService {
	
	@Autowired
	private WorshipsMapper worshipsMapper;

	@Override
	public int countByExample(Criteria example) {
		return worshipsMapper.countByExample(example);
	}

	@Override
	public List<Worship> selectByExample(Criteria example) {
		return worshipsMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveWorship(Criteria example) {
		Worship worship = (Worship) example.get("worship");
		
		int result = 0;
		if(worship.getId() == null) {
			result = worshipsMapper.insert(worship);
		} else {
			result = worshipsMapper.updateByPrimaryKey(worship);
		}
		
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		Integer id = example.getAsInteger("id");
		
		int result = worshipsMapper.deleteByPrimaryKey(id);
		
		return result > 0 ? "01" : "00";
	}

}
