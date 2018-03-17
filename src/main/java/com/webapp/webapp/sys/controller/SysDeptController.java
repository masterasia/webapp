package com.webapp.webapp.sys.controller;

import com.webapp.common.utils.Constant;
import com.webapp.common.utils.BaseResponce;
import com.webapp.webapp.sys.entity.SysDeptEntity;
import com.webapp.webapp.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * 部门管理
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;

    private static Long index = 0L;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public List<SysDeptEntity> list() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        return deptList;
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public BaseResponce select() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId("0");
            root.setDeptName("一级部门");
            root.setParentId("-1");
            root.setOpen(true);
            deptList.add(root);
        }

        return BaseResponce.ok().put("deptList", deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public BaseResponce info() {
        String deptId = "0";
        if (getUserId() != Constant.SUPER_ADMIN) {
            SysDeptEntity dept = sysDeptService.queryObject(getDeptId());
            deptId = dept.getParentId();
        }

        return BaseResponce.ok().put("deptId", deptId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public BaseResponce info(@PathVariable("deptId") Long deptId) {
        SysDeptEntity dept = sysDeptService.queryObject(deptId);

        return BaseResponce.ok().put("dept", dept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public BaseResponce save(@RequestBody SysDeptEntity dept) {
        dept.setDeptId(index++ + "");
        sysDeptService.save(dept);

        return BaseResponce.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public BaseResponce update(@RequestBody SysDeptEntity dept) {
        if (dept.getDeptId().equals(dept.getParentId())) {
            return BaseResponce.error("部门无法成为自身子部门");
        }
        sysDeptService.update(dept);

        return BaseResponce.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public BaseResponce delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return BaseResponce.error("请先删除子部门");
        }

        sysDeptService.delete(deptId);

        return BaseResponce.ok();
    }

}
