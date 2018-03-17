package com.webapp.webapp.sys.entity;

import java.io.Serializable;


/**
 * 部门管理
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-06-20 15:23:47
 */
public class SysAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//区域ID
	private String areaId;
	//上级区域ID，一级区域为0
	private String parentId;
	//区域名称
	private String areaName;
	//上级区域名称
	private String parentName;
	//排序
	private Integer orderNum;

	private String areaCode;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        return "SysAreaEntity{" +
                "areaId='" + areaId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", parentName='" + parentName + '\'' +
                ", orderNum=" + orderNum +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }
}
