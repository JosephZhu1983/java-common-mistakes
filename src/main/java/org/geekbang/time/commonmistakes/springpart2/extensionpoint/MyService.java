package org.geekbang.time.commonmistakes.springpart2.extensionpoint;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class MyService implements InitializingBean, DisposableBean, BeanNameAware {
    @Getter
    @Setter
    private int counter = 0;
    private String beanName;

    public MyService() {
        log.info("{}({}).constructor:{}", this, beanName, increaseCounter());
    }

    public int increaseCounter() {
        this.counter++;
        return counter;
    }

    public void hello() {
        log.info("{}({}).hello:{}", this, beanName, increaseCounter());
    }

    @PostConstruct
    public void postConstruct() {
        log.info("{}({}).postConstruct:{}", this, beanName, increaseCounter());
    }

    @Override
    public void afterPropertiesSet() {
        log.info("{}({}).afterPropertiesSet:{}", this, beanName, increaseCounter());
    }

    public void init() {
        log.info("{}({}).init:{}", this, beanName, increaseCounter());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("{}.preDestroy:{}", this, beanName, increaseCounter());
    }

    @Override
    public void destroy() {
        log.info("{}({}).destroy:{}", this, beanName, increaseCounter());
    }

    @Override
    public void setBeanName(String s) {
        log.info("{}({}).setBeanName:{}", this, beanName, increaseCounter());
        this.beanName = s;
    }
}
