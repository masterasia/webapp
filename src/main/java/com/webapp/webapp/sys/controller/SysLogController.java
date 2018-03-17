package com.webapp.webapp.sys.controller;


import com.webapp.common.utils.*;
import com.webapp.webapp.sys.entity.SysLogEntity;
import com.webapp.webapp.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 系统日志
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-03-08 10:40:56
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public BaseResponce list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SysLogEntity> sysLogList = sysLogService.queryList(query);
        int total = sysLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysLogList, total, query.getLimit(), query.getPage());

        return BaseResponce.ok().put("page", pageUtil);
    }

}
