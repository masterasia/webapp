package com.webapp.common.utils;

/**
 * Redis所有Keys
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }

    public static String getDictConfigKey(String key){
        return "sys:dict:" + key;
    }

}
