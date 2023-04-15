package org.geekbang.time.commonmistakes.springpart2.extensionpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("helloService");
        if (beanDefinition != null) {
            beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            beanDefinition.getPropertyValues().add("counter", 10);
        }
        log.info("MyBeanFactoryPostProcessor");
    }
}
