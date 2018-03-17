package com.webapp.common.utils;


import com.webapp.common.xss.SqlFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-03-14 23:15
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    //当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(null == params.get("page") || params.get("page").toString().isEmpty() ?
                "1" : params.get("page").toString());
        this.limit = Integer.parseInt(null == params.get("limit") || params.get("limit").toString().isEmpty() ?
                "1000" : params.get("limit").toString());
        this.put("offset", (page - 1) * limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = null == params.get("sidx") ? "" : params.get("sidx").toString();
        String order = null == params.get("order") ? "" : params.get("order").toString();
        String columns = null == params.get("columns") || params.get("columns").toString().isEmpty() ?
                "*" : params.get("columns").toString();
        this.put("sidx", SqlFilter.sqlInject(sidx));
        this.put("order", SqlFilter.sqlInject(order));
        this.put("columns", SqlFilter.sqlInject(columns));
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
