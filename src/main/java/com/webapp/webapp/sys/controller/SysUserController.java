package com.webapp.webapp.sys.controller;


import com.webapp.common.annotation.SysLog;
import com.webapp.webapp.sys.shiro.ShiroUtils;
import com.webapp.webapp.sys.entity.SysUserEntity;
import com.webapp.common.utils.PageUtils;
import com.webapp.common.utils.Query;
import com.webapp.common.utils.BaseResponce;
import com.webapp.common.validator.BaseAssert;
import com.webapp.common.validator.ValidatorUtils;
import com.webapp.common.validator.group.AddGroup;
import com.webapp.common.validator.group.UpdateGroup;
import com.webapp.webapp.sys.service.SysUserRoleService;
import com.webapp.webapp.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	private static Long index = 0L;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public BaseResponce list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return BaseResponce.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public BaseResponce info(){
		return BaseResponce.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public BaseResponce password(String password, String newPassword){
		BaseAssert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return BaseResponce.error("原密码不正确");
		}
		
		return BaseResponce.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public BaseResponce info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return BaseResponce.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public BaseResponce save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);

		user.setUserId(index++);
		sysUserService.save(user);
		
		return BaseResponce.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public BaseResponce update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return BaseResponce.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public BaseResponce delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return BaseResponce.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return BaseResponce.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return BaseResponce.ok();
	}
}
