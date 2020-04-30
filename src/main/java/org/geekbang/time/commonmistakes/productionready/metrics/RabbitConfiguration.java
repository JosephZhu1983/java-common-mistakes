package org.geekbang.time.commonmistakes.productionready.metrics;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    //队列
    @Bean
    public Queue queue() {
        return new Queue(Consts.QUEUE);
    }

    //交换器
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(Consts.EXCHANGE).durable(true).build();
    }

    //绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(Consts.ROUTING_KEY).noargs();
    }
}
