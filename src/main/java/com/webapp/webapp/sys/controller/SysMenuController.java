package com.webapp.webapp.sys.controller;

import com.webapp.common.annotation.SysLog;
import com.webapp.common.exception.WebException;
import com.webapp.common.utils.Constant;
import com.webapp.common.utils.BaseResponce;
import com.webapp.webapp.sys.entity.SysMenuEntity;
import com.webapp.webapp.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 系统菜单
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;

    private static Long index = 0L;

    /**
     * 导航菜单
     */
    @RequestMapping("/nav")
    public BaseResponce nav() {
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        return BaseResponce.ok().put("menuList", menuList);
    }

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public BaseResponce select() {
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setMenuName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return BaseResponce.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public BaseResponce info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.queryObject(menuId);
        return BaseResponce.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public BaseResponce save(@RequestBody SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        menu.setMenuId(index++);
        sysMenuService.save(menu);

        return BaseResponce.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public BaseResponce update(@RequestBody SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);
        if (menu.getMenuId().equals(menu.getParentId())) {
            return BaseResponce.error("菜单无法成为自身子菜单");
        }

        sysMenuService.update(menu);

        return BaseResponce.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public BaseResponce delete(long menuId) {
        int base = 31;
        if (menuId <= base) {
            return BaseResponce.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return BaseResponce.error("请先删除子菜单或按钮");
        }

        sysMenuService.deleteBatch(new Long[]{menuId});

        return BaseResponce.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getMenuName())) {
            throw new WebException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new WebException("上级菜单不能为空");
        }

        //菜单
        if (menu.getMenuType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new WebException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.queryObject(menu.getParentId());
            parentType = parentMenu.getMenuType();
        }

        //目录、菜单
        if (menu.getMenuType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getMenuType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new WebException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getMenuType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new WebException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
