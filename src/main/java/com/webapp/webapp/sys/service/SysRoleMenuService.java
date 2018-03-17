package com.webapp.webapp.sys.service;

import java.util.List;


/**
 * 角色与菜单对应关系
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年9月18日 上午9:42:30
 */
public interface SysRoleMenuService {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 删除角色关联菜单
	 * @param roleIds
	 */
	void deleteBatch(Long[] roleIds);

	void deleteBatchMenu(Long[] menuIds);

}
