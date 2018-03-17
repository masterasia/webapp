package com.webapp.webapp.sys.dao;

import com.webapp.webapp.sys.entity.SysAreaEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 区域管理
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-11-20 15:23:47
 */
@Mapper
public interface SysAreaDao extends BaseDao<SysAreaEntity> {

    /**
     * 查询子区域ID列表
     *
     * @param parentId 上级区域ID
     */
    List<String> queryAreaIdList(String parentId);
}
