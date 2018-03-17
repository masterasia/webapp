package com.webapp.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017/11/17 0017 上午 11:13
 * @description
 */
public class HttpClientUtil {

    public static String httpMethod(Constant.HttpMethod method, String url, List params) {
        String jsonParam = new String(JSON.toJSONBytes(params), StandardCharsets.UTF_8);
        return httpMethod(method, url, jsonParam);
    }

    public static String httpMethod(Constant.HttpMethod method, String url, List params, String paramName) {
        String jsonParam = paramName + "=" + new String(JSON.toJSONBytes(params), StandardCharsets.UTF_8);
        return httpMethod(method, url, jsonParam);
    }

    public static String httpMethod(Constant.HttpMethod method, String url, Map<String, Object> param){
        StringBuffer sb = new StringBuffer();
        param.entrySet().stream().forEach(en -> {
            sb.append(en.getKey()).append("=").append(JSON.toJSONString(en.getValue())).append("&");
        });
        String jsonParam = sb.deleteCharAt(sb.length() - 1).toString();
        return httpMethod(method, url, jsonParam);
    }

    public static String httpMethod(Constant.HttpMethod method, String url, Map params, String paramName) {
        String jsonParam = paramName + "=" + new String(JSON.toJSONBytes(params), StandardCharsets.UTF_8);
        return httpMethod(method, url, jsonParam);
    }

    public static String httpMethod(Constant.HttpMethod method, String url, String param) {
        System.out.println("[HttpClientUtil][httpMethod]" + param);
        switch (method) {
            case GET:
                return doGet(url, param);
            case POST:
                return doPost(url, param);
            default:
                return doGet(url, param);
        }
    }

    private static String doGet(String url, String jsonParam) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url + "?" + jsonParam);
        StringBuffer sb = new StringBuffer("");
        try {
            CloseableHttpResponse response = null;
            response = httpClient.execute(get);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                sb.append(EntityUtils.toString(response.getEntity()));
            }else {
                sb.append("try to get :" + url + " -with params :" + jsonParam );
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static String doPost(String url, String jsonParam) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        StringBuffer sb = new StringBuffer("");
        try {
            StringEntity json = new StringEntity(jsonParam);
            json.setContentType("application/x-www-form-urlencoded");
            json.setContentEncoding("UTF-8");
            post.setEntity(json);
            CloseableHttpResponse response = httpClient.execute(post);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                sb.append(EntityUtils.toString(response.getEntity()));
            } else {
                sb.append("error ~ :" + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
