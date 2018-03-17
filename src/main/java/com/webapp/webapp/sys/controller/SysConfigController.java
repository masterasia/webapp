package com.webapp.webapp.sys.controller;


import com.webapp.common.annotation.SysLog;
import com.webapp.webapp.sys.entity.SysConfigEntity;
import com.webapp.webapp.sys.service.SysConfigService;
import com.webapp.common.utils.PageUtils;
import com.webapp.common.utils.Query;
import com.webapp.common.utils.BaseResponce;
import com.webapp.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public BaseResponce list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysConfigEntity> configList = sysConfigService.queryList(query);
		int total = sysConfigService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());
		
		return BaseResponce.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public BaseResponce info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.queryObject(id);
		
		return BaseResponce.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public BaseResponce save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);
		
		return BaseResponce.ok();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public BaseResponce update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return BaseResponce.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public BaseResponce delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return BaseResponce.ok();
	}

}
