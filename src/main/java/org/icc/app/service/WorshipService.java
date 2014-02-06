package org.icc.app.service;

import java.util.List;

import org.icc.app.pojo.Criteria;
import org.icc.app.pojo.Worship;

public interface WorshipService {
	int countByExample(Criteria example);

	List<Worship> selectByExample(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveWorship(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);

}