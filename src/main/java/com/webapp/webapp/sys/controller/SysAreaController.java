package com.webapp.webapp.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webapp.common.utils.*;
import com.webapp.webapp.sys.entity.SysAreaEntity;
import com.webapp.webapp.sys.service.SysAreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 区域管理
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("sys/area")
public class SysAreaController extends AbstractController {
    @Autowired
    private SysAreaService sysAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:area:list")
    public BaseResponce list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo pageInfo = PageHelper.startPage(query.getPage(), query.getLimit()).doSelectPageInfo(() -> sysAreaService.queryList(query));

        return BaseResponce.ok().put("page", pageInfo);
    }

    /**
     * 列表
     */
    @RequestMapping("/tList")
    @RequiresPermissions("sys:area:list")
    public BaseResponce treeList(@RequestParam Map<String, Object> params) {
        List<SysAreaEntity> areaList = sysAreaService.queryList(params);

        return BaseResponce.ok().put("areaList", areaList);
    }

    /**
     * 选择区域(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:area:select")
    public BaseResponce select() {
        List<SysAreaEntity> areaList = sysAreaService.queryList(new HashMap<String, Object>());

        //添加一级区域
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysAreaEntity root = new SysAreaEntity();
            root.setAreaId("0");
            root.setAreaName("一级区域");
            root.setParentId("-1");
            areaList.add(root);
        }

        return BaseResponce.ok().put("areaList", areaList);
    }

    /**
     * 上级区域Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:Area:list")
    public BaseResponce info() {
        String areaId = "0";
        if (getUserId() != Constant.SUPER_ADMIN) {
            SysAreaEntity area = sysAreaService.queryObject(getDeptId().toString());
            areaId = area.getParentId();
        }

        return BaseResponce.ok().put("areaId", areaId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{AreaId}")
    @RequiresPermissions("sys:Area:info")
    public BaseResponce info(@PathVariable("AreaId") String areaId) {
        SysAreaEntity area = sysAreaService.queryObject(areaId);

        return BaseResponce.ok().put("area", area);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:Area:save")
    public BaseResponce save(@RequestBody SysAreaEntity area) {
        sysAreaService.save(area);

        return BaseResponce.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:Area:update")
    public BaseResponce update(@RequestBody SysAreaEntity area) {
        if (area.getAreaId().equals(area.getParentId())) {
            return BaseResponce.error("区域无法成为自身子区域");
        }
        sysAreaService.update(area);

        return BaseResponce.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:Area:delete")
    public BaseResponce delete(String areaId) {
        //判断是否有子区域
        List<String> areaList = sysAreaService.queryAreaIdList(areaId);
        if (areaList.size() > 0) {
            return BaseResponce.error("请先删除子区域");
        }

        sysAreaService.delete(areaId);

        return BaseResponce.ok();
    }

}
