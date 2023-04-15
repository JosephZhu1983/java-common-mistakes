package org.geekbang.time.commonmistakes.springpart2.extensionpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MyService) {
            log.info("{}({}).postProcessAfterInitialization:{}", bean, beanName, ((MyService) bean).increaseCounter());
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MyService) {
            log.info("{}({}).postProcessBeforeInitialization:{}", bean, beanName, ((MyService) bean).increaseCounter());
        }
        return bean;
    }
}
