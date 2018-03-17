package com.webapp.common.config;

import com.webapp.webapp.sys.shiro.ShiroTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.*;

import java.util.*;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017/12/26 0026 上午 09:21
 * @description
 */
@Configuration
public class FreemarkerConfig {
    @Autowired
    protected freemarker.template.Configuration configuration;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(ShiroTag shiroTag) {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/WEB-INF/views/");

        Map<String, Object> variables = new HashMap<>(1);
        variables.put("shiro", shiroTag);
        configurer.setFreemarkerVariables(variables);

        Properties settings = new Properties();
        settings.setProperty("default_encoding", "utf-8");
        settings.setProperty("number_format", "0.##");
        configurer.setFreemarkerSettings(settings);

        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver getFreemarkViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setCache(false);
        freeMarkerViewResolver.setSuffix(".html");
        freeMarkerViewResolver.setRequestContextAttribute("request");
        freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");
        freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
        return freeMarkerViewResolver;
    }
}
