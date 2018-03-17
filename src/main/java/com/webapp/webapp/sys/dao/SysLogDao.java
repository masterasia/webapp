package com.webapp.webapp.sys.dao;


import com.webapp.webapp.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity> {
	
}
