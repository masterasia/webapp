package com.webapp.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年10月27日 下午9:59:27
 */
public class BaseResponce extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public BaseResponce() {
		put("code", 200);
		put("msg", "you think your request is right?");
	}
	
	public static BaseResponce error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static BaseResponce error(String msg) {
		return error(500, msg);
	}
	
	public static BaseResponce error(int code, String msg) {
		BaseResponce r = new BaseResponce();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static BaseResponce ok(String msg) {
		BaseResponce r = new BaseResponce();
		r.put("msg", msg);
		return r;
	}
	
	public static BaseResponce ok(Map<String, Object> map) {
		BaseResponce r = new BaseResponce();
		r.putAll(map);
		return r;
	}
	
	public static BaseResponce ok() {
		return new BaseResponce();
	}

	@Override
    public BaseResponce put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
