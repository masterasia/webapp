package com.webapp.webapp.sys.service.impl;

import com.webapp.webapp.sys.dao.SysLogDao;
import com.webapp.webapp.sys.entity.SysLogEntity;
import com.webapp.webapp.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2018/1/11 0011 上午 11:39
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public SysLogEntity queryObject(Long id) {
        return sysLogDao.queryObject(id);
    }

    @Override
    public List<SysLogEntity> queryList(Map<String, Object> map) {
        return sysLogDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysLogDao.queryTotal(map);
    }

    @Override
    public void save(SysLogEntity sysLog) {
        sysLogDao.save(sysLog);
    }

    @Override
    public void update(SysLogEntity sysLog) {
        sysLogDao.update(sysLog);
    }

    @Override
    public void delete(Long logId) {
        sysLogDao.delete(logId);
    }

    @Override
    public void deleteBatch(Long[] logIds) {
        sysLogDao.deleteBatch(logIds);
    }

}
