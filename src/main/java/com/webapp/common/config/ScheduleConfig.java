package com.webapp.common.config;

import com.webapp.common.utils.SpringContextUtils;
import com.webapp.common.utils.SysPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017/12/26 0026 上午 09:26
 * @description
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);

        //quartz参数
        Properties prop = new Properties();
        SysPropertySource sys = (SysPropertySource) SpringContextUtils.getBean("sysPropertySource");
        prop.putAll(sys.getSchedule());
        factory.setQuartzProperties(prop);

        factory.setSchedulerName((String) sys.getSchedule().get("schedulerName"));
        //延时启动
        factory.setStartupDelay((Integer) sys.getSchedule().get("startupDelay"));
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs((Boolean) sys.getSchedule().get("overwriteExistingJobs"));
        //设置自动启动，默认为true
        factory.setAutoStartup((Boolean) sys.getSchedule().get("autoStartup"));

        return factory;
    }
}
