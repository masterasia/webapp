package com.webapp.common.utils;

import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * 返回数据
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017年10月27日 下午9:59:27
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResponce {
    @XmlElement
    private String name;
    @XmlElement
    private Integer sex;
    @XmlElement
    private Integer age;
    @XmlElement
    private Integer status = 0;

    private Date birthday;

    public XmlResponce(String name, Integer sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public XmlResponce() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
