package org.geekbang.time.commonmistakes.logging.mdc;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        System.setProperty("logging.config", "classpath:org/geekbang/time/commonmistakes/logging/mdc/logback.xml");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @Bean
    FilterRegistrationBean<MDCInsertingServletFilter> mdcFilterRegistrationBean() {
        FilterRegistrationBean<MDCInsertingServletFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MDCInsertingServletFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }

    @Bean
    FilterRegistrationBean<LogReqIdFilter> reqIdFilterRegistrationBean() {
        FilterRegistrationBean<LogReqIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogReqIdFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }
}

