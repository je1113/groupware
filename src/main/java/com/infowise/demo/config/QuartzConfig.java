package com.infowise.demo.config;

import com.infowise.demo.component.EmailJob;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzConfig {
    @Bean
    public JobDetailFactoryBean emailJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(EmailJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean emailTrigger(JobDetail emailJobDetail) {
        CronTriggerFactoryBean  factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(emailJobDetail);
        factoryBean.setCronExpression("0 0 9 ? * FRI");
        return factoryBean;
    }
}
