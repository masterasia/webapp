package com.webapp.webapp.sys.service.impl;

import com.webapp.common.annotation.DataFilter;
import com.webapp.webapp.sys.dao.SysAreaDao;
import com.webapp.webapp.sys.entity.SysAreaEntity;
import com.webapp.webapp.sys.service.SysAreaService;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2018/1/11 0011 上午 11:37
 */
@Service("sysAreaService")
public class SysAreaServiceImpl implements SysAreaService {
    @Autowired
    private SysAreaDao sysAreaDao;

    @Override
    public SysAreaEntity queryObject(String areaId) {
        return sysAreaDao.queryObject(areaId);
    }

    @Override
    @DataFilter(tableAlias = "d", user = false)
    public List<SysAreaEntity> queryList(Map<String, Object> map) {
        return sysAreaDao.queryList(map);
    }

    @Override
    public void save(SysAreaEntity sysArea) {
        sysAreaDao.save(sysArea);
    }

    @Override
    public void update(SysAreaEntity sysArea) {
        sysAreaDao.update(sysArea);
    }

    @Override
    public void delete(String areaId) {
        sysAreaDao.delete(areaId);
    }

    @Override
    public List<String> queryAreaIdList(String parentId) {
        return sysAreaDao.queryAreaIdList(parentId);
    }

    @Override
    public String getSubAreaIdList(String areaId) {
        //区域及子区域ID列表
        List<String> areaIdList = new ArrayList<>();

        //获取子区域ID
        List<String> subIdList = queryAreaIdList(areaId);
        getAreaTreeList(subIdList, areaIdList);

        //添加本区域
        areaIdList.add(areaId);

        String areaFilter = StringUtils.join(areaIdList, ",");
        return areaFilter;
    }

    /**
     * 递归
     */
    private void getAreaTreeList(List<String> subIdList, List<String> areaIdList) {
        for (String areaId : subIdList) {
            List<String> list = queryAreaIdList(areaId);
            if (list.size() > 0) {
                getAreaTreeList(list, areaIdList);
            }

            areaIdList.add(areaId);
        }
    }
}
