package com.webapp.webapp.sys.service;


import com.webapp.webapp.sys.entity.SysAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * 区域管理
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-11-20 15:23:47
 */
public interface SysAreaService {

    SysAreaEntity queryObject(String areaId);

    List<SysAreaEntity> queryList(Map<String, Object> map);

    void save(SysAreaEntity sysArea);

    void update(SysAreaEntity sysArea);

    void delete(String areaId);

    /**
     * 查询子区域ID列表
     *
     * @param parentId 上级区域ID
     */
    List<String> queryAreaIdList(String parentId);

    /**
     * 获取子区域ID(包含本区域ID)，用于数据过滤
     */
    String getSubAreaIdList(String areaId);

}
