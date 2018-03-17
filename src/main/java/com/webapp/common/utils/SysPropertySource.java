package com.webapp.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

import java.util.Map;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2018/1/3 0003 上午 09:19
 * @description
 */
@Configuration
@ConfigurationProperties(prefix = "sys", ignoreUnknownFields = false)
@PropertySource("classpath:/config/sys-config.properties")
public class SysPropertySource {

    private Map<String, String> redis;

    private Map<String, Object> shiro;

    private Map<String, Object> schedule;

    private Map<String, Object> session;

    public Map<String, String> getRedis() {
        return redis;
    }

    public void setRedis(Map<String, String> redis) {
        this.redis = redis;
    }

    public Map<String, Object> getShiro() {
        return shiro;
    }

    public void setShiro(Map<String, Object> shiro) {
        this.shiro = shiro;
    }

    public Map<String, Object> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, Object> schedule) {
        this.schedule = schedule;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
