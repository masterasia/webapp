package com.webapp.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017/12/26 0026 上午 09:22
 * @description
 */
@Configuration
public class ResourcesConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/swagger/");
    }
}
