package org.mob.app.service;

import java.util.List;

import org.mob.app.pojo.Criteria;
import org.mob.app.pojo.Worship;

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