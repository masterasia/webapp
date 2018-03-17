package com.webapp.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年10月27日 下午9:59:27
 */
public class ServiceR extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ServiceR() {
        put("status", 0);
        put("data", "");
    }

    public static ServiceR error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ServiceR error(String msg) {
        return error(500, msg);
    }

    public static ServiceR error(int code, String msg) {
        ServiceR r = new ServiceR();
        r.put("status", code);
        r.put("data", msg);
        return r;
    }

    public static ServiceR ok(String msg) {
        ServiceR r = new ServiceR();
        r.put("data", msg);
        return r;
    }

    public static ServiceR ok(Map<String, Object> map) {
        ServiceR r = new ServiceR();
        r.putAll(map);
        return r;
    }

    public static ServiceR ok() {
        return new ServiceR();
    }

    @Override
    public ServiceR put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        this.entrySet().stream().forEach(en -> {
            sb.append(en.getKey()).append("=").append(JSON.toJSONString(en.getValue())).append("&");
        });
        String jsonParam = sb.deleteCharAt(sb.length() - 1).toString();
        return jsonParam;
    }
}
