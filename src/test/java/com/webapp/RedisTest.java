package com.webapp;

import com.webapp.common.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mvc.xml","classpath:spring-jdbc.xml",
        "classpath:spring-redis.xml","classpath:spring-scheduler.xml",})
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test(){
        //测试redis，需要设置pmis.redis.open=true
        redisUtils.set("domain", "");
        String domain = redisUtils.get("domain");

        System.out.println(domain);
    }

    @Test
    public void re(){
        System.out.println("\u7C7B\u578B\u8F6C\u6362\uFF0C\u914D\u7F6E\u4FE1\u606F");
    }

    @Test
    public void maxmin(){
        System.out.println(Long.MAX_VALUE);
    }
}
