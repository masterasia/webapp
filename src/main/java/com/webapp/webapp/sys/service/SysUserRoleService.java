package com.webapp.webapp.sys.service;

import java.util.List;


/**
 * 用户与角色对应关系
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年9月18日 上午9:43:24
 */
public interface SysUserRoleService {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
	
	void delete(Long userId);

	/**
	 * 删除角色关联
	 * @param roleIds
	 */
	void deleteBatch(Long[] roleIds);

	void deleteBatchUser(Long[] userIds);
}
