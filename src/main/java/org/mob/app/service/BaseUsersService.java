package org.mob.app.service;

import java.util.List;

import org.mob.app.pojo.BaseUsers;
import org.mob.app.pojo.Criteria;

public interface BaseUsersService {
	int countByExample(Criteria example);

	BaseUsers selectByPrimaryKey(String userId);

	List<BaseUsers> selectByExample(Criteria example);

	/**
	 * 更新用户的密码
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String updatePassword(Criteria example);
	/**
	 * 找回用户的密码
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String findPassword(Criteria example)throws Exception;
	/**
	 * 重置用户的密码
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String resetPwdByPrimaryKey(Criteria example);

	/**
	 * 根据主键删除
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String deleteByPrimaryKey(Criteria example);

	/**
	 * 用户登录查找
	 * 
	 * @param criteria
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String selectByBaseUser(Criteria criteria);

	/**
	 * 验证用户名是否注册
	 * 
	 * @param example
	 * @return 00：已经注册，01：未注册 ,其他情况
	 */
	String validateAccount(Criteria example);

	/**
	 * 更新用户密码
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String updateUserPassword(Criteria example);

	/**
	 * 保存用户
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveUser(Criteria example);

	/**
	 * 保存用户自己更新的信息
	 * 
	 * @param user
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String updateByPrimaryKeySelective(BaseUsers user);
}