package com.webapp.webapp.sys.service.impl;

import com.webapp.common.annotation.DataFilter;
import com.webapp.webapp.sys.dao.SysRoleDao;
import com.webapp.webapp.sys.entity.SysRoleEntity;
import com.webapp.webapp.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	@Override
	@DataFilter(tableAlias = "r", user = false)
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	@Override
	@DataFilter(tableAlias = "r", user = false)
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());

		sysRoleDao.save(role);
		
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId().longValue(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId().longValue(), role.getDeptIdList());
	}

	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleDao.update(role);
		
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId().longValue(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId().longValue(), role.getDeptIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
        //删除角色与菜单关系
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与部门关系
        sysRoleDeptService.deleteBatch(roleIds);
        //删除角色绑定用户关系
        sysUserRoleService.deleteBatch(roleIds);
		sysRoleDao.deleteBatch(roleIds);
	}

}
