package com.webapp.webapp.job.dao;

import com.webapp.webapp.sys.dao.BaseDao;
import com.webapp.webapp.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}
