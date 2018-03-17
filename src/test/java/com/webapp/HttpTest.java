package com.webapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017/11/17 0017 上午 11:38
 * @description
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class HttpTest {

    @Test
    public void test() throws Exception {
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);// 允许连接提交信息
        connection.setRequestMethod("POST");// 网页提交方式“GET”、“POST”
        String content = "results=1";

        OutputStream os = connection.getOutputStream();
        os.write(content.toString().getBytes("UTF-8"));
        os.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        System.out.println(connection.getResponseCode());
        System.out.println(connection.getResponseMessage());
// 输出内容
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }

    }
}
