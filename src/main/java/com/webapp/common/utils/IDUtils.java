package com.webapp.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017/11/21 0021 上午 09:43
 * @description
 */
public class IDUtils {
    private static Logger logger = LoggerFactory.getLogger(IpUtils.class);

    /**
     * 获取带连字符 - 标准格式的UUID字符串
     * @return
     */
    public static String getUUIDWithHyphen(){
        return UUID.randomUUID().toString();
    }

    /**
     * 获取不带连字符- 格式的UUID字符串
     * @return
     */
    public static String getUUIDWithoutHyphen(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
