package com.webapp.webapp.job.controller;

import com.webapp.common.utils.*;
import com.webapp.webapp.job.entity.ScheduleJobLogEntity;
import com.webapp.webapp.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public BaseResponce list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(query);
        int total = scheduleJobLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());

        return BaseResponce.ok().put("page", pageUtil);
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public BaseResponce info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);

        return BaseResponce.ok().put("log", log);
    }
}
