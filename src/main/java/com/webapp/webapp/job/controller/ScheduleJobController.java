package com.webapp.webapp.job.controller;

import com.webapp.common.annotation.SysLog;
import com.webapp.common.utils.*;
import com.webapp.common.validator.ValidatorUtils;
import com.webapp.webapp.job.entity.ScheduleJobEntity;
import com.webapp.webapp.job.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年11月28日 下午2:16:40
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    private static Long index = 0L;

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public BaseResponce list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
        int total = scheduleJobService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());

        return BaseResponce.ok().put("page", pageUtil);
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public BaseResponce info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);

        return BaseResponce.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public BaseResponce save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);
        scheduleJob.setJobId(index++);
        scheduleJobService.save(scheduleJob);

        return BaseResponce.ok();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public BaseResponce update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return BaseResponce.ok();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public BaseResponce delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return BaseResponce.ok();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public BaseResponce run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);

        return BaseResponce.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public BaseResponce pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);

        return BaseResponce.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public BaseResponce resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);

        return BaseResponce.ok();
    }

}
